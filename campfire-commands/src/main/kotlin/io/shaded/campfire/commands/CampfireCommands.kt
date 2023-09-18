package io.shaded.campfire.commands

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

  }

  /**
   *
   */
  fun suggestions(sender: S, input: String): Iterable<String> {
    return emptyList()
  }

}
