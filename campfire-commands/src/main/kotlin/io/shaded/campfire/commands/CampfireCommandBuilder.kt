package io.shaded.campfire.commands

import io.shaded.campfire.commands.annotations.CampfireCommandDsl

/**
 * An abstract builder for creating instances of [CampfireCommand].
 *
 * @param S The type of the sender for the command.
 */
abstract class CampfireCommandBuilder<S> {

  /**
   * Abstract method to be implemented by subclasses for building a [CampfireCommand].
   *
   * @return A [CampfireCommand] instance.
   */
  abstract fun build(): CampfireCommand<S>

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
  protected fun <S> command(
    name: String, vararg aliases: String, init: CampfireCommand<S>.() -> Unit
  ): CampfireCommand<S> {
    return CampfireCommand<S>(name, aliases).apply(init)
  }

}
