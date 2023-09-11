package io.shaded.campfire.commands.argument.parser

import io.shaded.campfire.commands.StringTokenizer
import io.shaded.campfire.commands.argument.CommandArgument
import io.shaded.campfire.commands.exception.CommandSyntaxException

/**
 * A command argument that represents an Long value.
 */
class LongArgument(private val min: Long = Long.MIN_VALUE, private val max: Long = Long.MAX_VALUE) :
  CommandArgument<Long>() {

  /**
   * Parses the next token as an Long value.
   * @param tokenizer The string tokenizer to use for parsing.
   * @throws [CommandSyntaxException] Thrown if the token is not a valid Long value or if the value is not
   * between [min] and [max].
   */
  override fun parse(tokenizer: StringTokenizer) {
    val token = tokenizer.next() ?: throw CommandSyntaxException("Expected Long")

    val long = token.toLongOrNull() ?: throw CommandSyntaxException("Invalid Long '$token'")

    if (long > max) {
      throw CommandSyntaxException("Long must not be more than $max, found $long")
    } else if (long < min) {
      throw CommandSyntaxException("Long must not be less than $min, found $long")
    } else {
      this.parsed = long
    }
  }

  /**
   * Gets a list of suggested Long values based on the next token.
   * @param tokenizer The string tokenizer to use for suggesting values.
   * @return A list of suggested Long values.
   */
  override fun suggestions(tokenizer: StringTokenizer): Iterable<String> = SUGGESTIONS

  companion object {
    private val SUGGESTIONS = listOf("-1", "0", "1")
  }
}
