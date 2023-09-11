package io.shaded.campfire.commands.argument

import io.shaded.campfire.commands.StringTokenizer
import io.shaded.campfire.commands.exception.CommandSyntaxException
import kotlin.reflect.KProperty

/**
 * Abstract class for defining a command argument.
 * @param T The type of the parsed argument.
 */
abstract class CommandArgument<T : Any> {
  lateinit var parsed: T

  /**
   * Gets the parsed argument value.
   * @param thisRef This reference (ignored).
   * @param property The property (ignored).
   * @return The parsed argument value.
   */
  operator fun getValue(thisRef: Any?, property: KProperty<*>): T = this.parsed

  /**
   * Parses the token and sets the parsed value.
   * @param tokenizer The token to parse.
   * @throws [CommandSyntaxException] Thrown if argument fails to parse.
   */
  @kotlin.jvm.Throws(CommandSyntaxException::class)
  abstract fun parse(tokenizer: StringTokenizer)

  /**
   * Gets a list of suggested values for the argument.
   * @param tokenizer The token to provide suggestions for.
   * @return A list of suggested values for the argument.
   */
  open fun suggestions(tokenizer: StringTokenizer): Iterable<String> =
    emptyList()
}
