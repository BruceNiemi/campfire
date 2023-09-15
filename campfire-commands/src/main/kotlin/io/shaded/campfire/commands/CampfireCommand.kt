package io.shaded.campfire.commands

import io.shaded.campfire.commands.annotations.CampfireCommandDsl

/**
 * Represents an action associated with a command, which is executed only
 * when the preconditions for the command are met.
 *
 * @param S The type of the sender for the command.
 */
typealias CommandAction<S> = S.() -> Unit

/**
 * Represents a function that determines whether a sender can execute a
 * specific command.
 *
 * @param S The type of the sender for the command.
 * @return `true` if the sender is allowed to execute the command; `false` otherwise.
 */
typealias CommandCondition<S> = (sender: S, input: String?) -> Boolean

class CampfireCommand<S>(val name: String, val aliases: Array<out String>) {
  private val conditions = hashSetOf<CommandCondition<S>>()
  lateinit var body: CommandAction<S>


  /**
   * Sets the action to be executed when this command is invoked.
   *
   * @param action The action to be executed when the command is invoked.
   */
  @CampfireCommandDsl
  fun execute(action: CommandAction<S>) {
    check(this::body.isInitialized) {
      "The command executor can only be set once."
    }

    body = action
  }

  /**
   * Sets a condition to be checked before this command is invoked.
   *
   * @param condition The condition to be checked when the command is invoked.
   */
  @CampfireCommandDsl
  fun condition(condition: CommandCondition<S>) {
    this.conditions.add(condition)
  }

  /**
   * A DSL function to create and configure a [CampfireCommand] instance.
   *
   * This function allows for a concise way to create and configure a [CampfireCommand].
   *
   * @param name The name of the command.
   * @param aliases The command aliases.
   * @param init A lambda expression that configures the [CampfireCommand].
   * @return A configured [CampfireCommand] instance.
   */
  @CampfireCommandDsl
  fun command(
    name: String, vararg aliases: String, init: CampfireCommand<S>.() -> Unit
  ): CampfireCommand<S> {
    return CampfireCommand<S>(name, aliases).apply(init)
  }

}
