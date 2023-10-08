package io.shaded.campfire.commands

import io.shaded.campfire.commands.exception.CommandSyntaxException
import io.shaded.campfire.commands.exception.NoInputTokenException
import io.shaded.campfire.commands.map.CommandMap
import java.util.*

/**
 * @param S The type of the sender for the command.
 */
abstract class CampfireCommands<S>(
  prefix: String
) {
  val commandMap = CommandMap<S>(prefix)

  fun register(
    name: String,
    vararg aliases: String,
    init: CampfireCommand<S>.() -> Unit
  ) {
    val command = CampfireCommand<S>(name, aliases).apply(init)
    this.register(command)
  }

  /**
   *
   */
  fun register(builder: CampfireCommandBuilder<S>): Boolean =
    this.register(builder.build())

  /**
   *
   */
  abstract fun register(command: CampfireCommand<S>): Boolean

  sealed class CommandExecutionResult {
    object EXECUTED : CommandExecutionResult()
    object CANCELLED : CommandExecutionResult()
    object UNKNOWN : CommandExecutionResult()
    object PRECONDITION_FAILED : CommandExecutionResult()
    object EXECUTOR_EXCEPTION : CommandExecutionResult()
    class INVALID_SYNTAX(val message: String) : CommandExecutionResult()
  }

  /**
   *
   */
  fun dispatch(sender: S, input: String): CommandExecutionResult {
    val inputQueue = tokenize(input)

    val command = this.findCommand(inputQueue)
      ?: return CommandExecutionResult.UNKNOWN

    if (!command.hasBody) {
      return CommandExecutionResult.CANCELLED
    }

    val preconditions = command.conditions.all {
      it.invoke(
        sender,
        inputQueue
      )
    }

    if (!preconditions) {
      return CommandExecutionResult.PRECONDITION_FAILED
    }

    // Now we have to parse the arguments.
    try {
      command.arguments
        .forEach { argument -> argument.parse(sender, inputQueue) }
    } catch (ex: CommandSyntaxException) {
      return CommandExecutionResult.INVALID_SYNTAX(ex.message)
    } catch (ex: NoInputTokenException) {
      return CommandExecutionResult.INVALID_SYNTAX(ex.message)
    }

    try {
      command.body.invoke(sender)
    } catch (exception: Exception) {
      exception.printStackTrace() // We still need to see the error from this.
      return CommandExecutionResult.EXECUTOR_EXCEPTION
    }

    return CommandExecutionResult.EXECUTED
  }


  fun findCommand(input: Queue<String>): CampfireCommand<S>? {
    val token = input.poll() ?: return null

    return findCommandRec(input, commandMap.getCommand(token))
  }

  private tailrec fun findCommandRec(
    input: Queue<String>,
    command: CampfireCommand<S>?
  ): CampfireCommand<S>? {
    if (command == null) {
      return null
    }

    if (command.children.isEmpty()) {
      return command
    }

    if (input.isEmpty()) {
      return null
    }

    return findCommandRec(input, command.children[input.poll()])
  }

  /**
   *
   */
  fun suggestions(sender: S, input: String): List<String> {
    val inputQueue = tokenize(input)

    // We should never not have anything in the root.
    if (inputQueue.isEmpty()) {
      return this.commandMap.getCommandLabels()
    }

    return emptyList()
  }


  fun tokenize(input: String): LinkedList<String> {
    val buildList = LinkedList<String>()
    val tokenizer = StringTokenizer(input, " ")

    while (tokenizer.hasMoreElements()) {
      buildList.add(tokenizer.nextToken())
    }

    return buildList
  }
}
