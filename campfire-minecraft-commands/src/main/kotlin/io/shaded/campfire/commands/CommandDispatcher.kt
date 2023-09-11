package io.shaded.campfire.commands

import io.shaded.campfire.commands.exception.CommandSyntaxException

abstract class CommandDispatcher<S> {
  private val commandTree = CommandTree()

  @CommandDSL
  fun command(vararg labels: String, init: Command<S>.() -> Unit) {
    val aliases = labels.toMutableList()

    if (aliases.isNotEmpty()) {
      aliases.removeFirst()
    }

    val command = Command<S>(labels[0], aliases).apply(init)
    this.commandTree.insert(labels.toList(), command)
    this.register(command)
  }

  protected abstract fun register(command: Command<S>)

  fun execute(input: String, source: S): CommandResult {
    val tokenizer = StringTokenizer(input)

    if (!tokenizer.hasNext()) {
      throw IllegalArgumentException("Commands with empty input cannot be execute")
    }

    // Now we need to recursively scan the commands
    // firstly we must use CommandTree#getCommand
    //  if subCommands != null then
    //    pop the current input queue (if any)
    //  then stop when either subCommands == null

    var command =
      this.findCommand(tokenizer, this.commandTree.getCommand(tokenizer.next()!!))
        ?: return CommandResult.UnknownCommand

    // Fuck it - Bruce
    command = command as Command<S>

    if (!command.hasBody) return CommandResult.NoBody

    if (command.hasPreconditions) {
      val result = command.preconditions.evaluate(source)

      if (result != null) {
        return CommandResult.FailedPrecondition(result)
      }
    }

    try {
      if (command.arguments.isNotEmpty()) {
        command
          .arguments
          .forEach { it.parse(tokenizer) }
      }

      command.body(source)
    } catch (ex: CommandSyntaxException) {
      return CommandResult.FailedParseArguments(ex.message)
    } catch (ex: Exception) {
      ex.printStackTrace()
      return CommandResult.Error
    }

    return CommandResult.ExecutedCommand
  }

  fun suggest(input: String): List<String> {
    val tokenizer = StringTokenizer(input)
    val suggestions = mutableListOf<String>()

    // Handle if there is no user input.
    if (!tokenizer.hasNext()) {
      return commandTree.getLabels()
    }

    // Find the command path that we are trying to execute upon.
    var command = commandTree.getCommand(tokenizer.next()!!)

    while (tokenizer.hasNext() && command != null && command.subCommands != null) {
      command = command.subCommands!!.getCommand(tokenizer.next()!!)
    }

    if (command == null) {
      return suggestions
    }

    if (command.subCommands != null) {
      suggestions.addAll(command.subCommands!!.getLabels())
    } else {
      // We've reached the end of the command, return nothing.
      if (tokenizer.size() >= command.arguments.size) {
        return suggestions
      }

      suggestions.addAll(command.arguments[tokenizer.size()].suggestions(tokenizer))
    }

    return suggestions
  }


  private fun findCommand(tokenizer: StringTokenizer, command: Command<*>?): Command<*>? {
    if (command == null) {
      return null
    }

    if (command.subCommands == null) {
      return command
    }

    if (!tokenizer.hasNext()) {
      return null
    }

    return findCommand(tokenizer, command.subCommands!!.getCommand(tokenizer.next()!!))
  }

  /**
   * THIS METHOD IS LEFT VISIBLE FOR UNIT TESTING NEVER
   * USE THIS METHOD OUTSIDE THE CONTEXT OF THIS CLASS.
   */
  fun getCommand(label: String): Command<*>? {
    return this.commandTree.getCommand(label)
  }

  override fun toString(): String {
    return "CommandDispatcher(commandTree=$commandTree)"
  }
}
