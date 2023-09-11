package io.shaded.campfire.commands.argument.parser

import io.shaded.campfire.commands.StringTokenizer
import io.shaded.campfire.commands.argument.CommandArgument
import io.shaded.campfire.commands.exception.CommandSyntaxException

/**
 * A command argument that represents an Integer value.
 */
class IntegerArgument(
  private val min: Int = Int.MIN_VALUE,
  private val max: Int = Int.MAX_VALUE
) : CommandArgument<Int>() {

  /**
   * Parses the next token as an Integer value.
   * @param tokenizer The string tokenizer to use for parsing.
   * @throws [CommandSyntaxException] Thrown if the token is not a valid Integer value or if the value is not
   * between [min] and [max].
   */
  override fun parse(tokenizer: StringTokenizer) {
    val token =
      tokenizer.next() ?: throw CommandSyntaxException("Expected Integer")

    val integer = token.toIntOrNull()
      ?: throw CommandSyntaxException("Invalid Integer '$token'")

    if (integer > max) {
      throw CommandSyntaxException("Integer must not be more than $max, found $integer")
    } else if (integer < min) {
      throw CommandSyntaxException("Integer must not be less than $min, found $integer")
    } else {
      this.parsed = integer
    }
  }

  /**
   * Gets a list of suggested Integer values based on the next token.
   * @param tokenizer The string tokenizer to use for suggesting values.
   * @return A list of suggested Integer values.
   */
  override fun suggestions(tokenizer: StringTokenizer): Iterable<String> =
    SUGGESTIONS

  companion object {
    private val SUGGESTIONS = listOf("-1", "0", "1")
  }
}
