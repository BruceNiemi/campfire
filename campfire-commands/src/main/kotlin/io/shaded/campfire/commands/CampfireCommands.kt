package io.shaded.campfire.commands

import io.shaded.campfire.commands.exception.CommandNotFoundException
import io.shaded.campfire.commands.map.CommandMap
import io.shaded.campfire.commands.map.Platform
import java.util.*

/**
 * @param S The type of the sender for the command.
 */
abstract class CampfireCommands<S>(
  prefix: String,
  platform: Platform<S>
) {
  val commandMap = CommandMap(prefix, platform)

  /**
   *
   */
  fun register(builder: CampfireCommandBuilder<S>): Boolean =
    this.register(builder.build())

  /**
   *
   */
  abstract fun register(command: CampfireCommand<S>): Boolean

  /**
   *
   */
  fun dispatch(sender: S, input: String) {
    val inputQueue = tokenize(input)

    val command = this.findCommand(inputQueue)
      ?: throw CommandNotFoundException("Oops!, could not execute command.")

    println("trying to run $command")

    // Now we have to parse the arguments.
    command.arguments
      .forEach { argument -> argument.parse(sender, inputQueue) }

    if (command.hasBody &&
      command.conditions.all { it.invoke(sender, inputQueue) }
    ) {
      command.body.invoke(sender)
    }
  }


  private fun findCommand(input: Queue<String>): CampfireCommand<S>? {
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
  fun suggestions(sender: S, input: String): Iterable<String> {
    val inputQueue = tokenize(input)

    // There is nothing in the input queue so we can just return the labels.
    if (inputQueue.isEmpty()) {
      return this.commandMap.getCommandLabels()
    }

    var command = this.commandMap.getCommand(inputQueue.poll())

    while (inputQueue.isNotEmpty()
      && command != null
      && command.children.isNotEmpty()
    ) {
      command = command.children[inputQueue.poll()]
    }

    if (command == null) {
      return emptyList()
    }

    if (command.children.isNotEmpty()) {
      return command.children.keys.toList()
    } else {
      if (inputQueue.size >= command.arguments.size) {
        return emptyList()
      }

      return command.arguments[inputQueue.size].suggest(sender, inputQueue)
    }

    return emptyList()
  }


  private fun tokenize(input: String): LinkedList<String> {
    val buildList = LinkedList<String>()
    val tokenizer = StringTokenizer(input, " ")

    while (tokenizer.hasMoreElements()) {
      buildList.add(tokenizer.nextToken())
    }

    if (input.endsWith(" ")) {
      buildList.add("")
    }

    return buildList
  }
}
