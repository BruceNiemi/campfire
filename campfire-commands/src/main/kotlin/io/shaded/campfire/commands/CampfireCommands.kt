package io.shaded.campfire.commands

import java.util.*

/**
 * @param S The type of the sender for the command.
 */
abstract class CampfireCommands<S> {

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
