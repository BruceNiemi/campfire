package io.shaded.campfire.commands.argument

import java.util.*

/**
 * @param S The type of the sender for the command.
 * @param T The type of argument is being parsed.
 */
interface ArgumentParser<S, T : Any?> {

  /**
   * A sealed class representing the result of parsing an argument. Instances
   * of this class can be either a successful result or an error result.
   */
  sealed class ArgumentParseResult<T> {
    class Success<T : Any?>(val value: T) : ArgumentParseResult<T>()
    class Error<T : Any?>(val error: String) : ArgumentParseResult<T>()
  }

  /**
   *
   */
  fun parse(input: Queue<String>): ArgumentParseResult<T>

  /**
   *
   */
  fun suggestions(sender: S, input: Queue<String>): Iterable<String> {
    return emptyList()
  }

}
