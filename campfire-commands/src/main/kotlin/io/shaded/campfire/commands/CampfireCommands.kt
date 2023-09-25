package io.shaded.campfire.commands

import io.shaded.campfire.commands.exception.CommandNotFoundException
import io.shaded.campfire.commands.map.CommandMap
import io.shaded.campfire.commands.map.Platform
import java.util.*

/**
 * @param S The type of the sender for the command.
 */
abstract class CampfireCommands<S>(
  val prefix: String,
  val platform: Platform<S>
) {
  private val commandMap = CommandMap(prefix, platform)

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

    // We should never not have input here but checking edge cases I guess.
    val commandLabel = inputQueue.poll()
      ?: throw CommandNotFoundException("Oops!, could not execute command.")

    val command = this.findCommandRec(
      inputQueue, this.commandMap.getCommand(commandLabel)
    ) ?: throw CommandNotFoundException("Oops!, could not execute command.")

    // Now we have to parse the arguments.
    command.arguments
      .forEach { argument -> argument.parse(sender, inputQueue) }

    if (command.hasBody &&
      command.conditions.all { it.invoke(sender, inputQueue) }) {
      command.body.invoke(sender)
    }
  }

  private tailrec fun findCommandRec(
    input: Queue<String>,
    command: CampfireCommand<S>?
  ): CampfireCommand<S>? {
    // Base case.
    if (command == null) return null

    val label = input.poll() ?: return null

    // Check if the label exists as a child of the current command.
    val childCommand = command.children[label]

    return if (childCommand != null) {
      // If a child command with the given label exists, recursively search in it.
      findCommandRec(input, childCommand)
    } else {
      childCommand
    }
  }

  /**
   *
   */
  fun suggestions(sender: S, input: String): Iterable<String> {
    val inputQueue = tokenize(input)


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
