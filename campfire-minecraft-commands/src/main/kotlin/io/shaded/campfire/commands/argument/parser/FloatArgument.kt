package io.shaded.campfire.commands.argument.parser

import io.shaded.campfire.commands.StringTokenizer
import io.shaded.campfire.commands.argument.CommandArgument
import io.shaded.campfire.commands.exception.CommandSyntaxException
import kotlin.math.floor
import kotlin.math.pow

/**
 * A command argument that represents a Float value.
 */
class FloatArgument(
  private val min: Float = Float.NEGATIVE_INFINITY,
  private val max: Float = Float.POSITIVE_INFINITY,
  private val truncationLimit: Int = -1
) : CommandArgument<Float>() {

  init {
    require(truncationLimit == -1 || this.truncationLimit > 0) {
      "Truncation value must be greater than zero"
    }
  }

  /**
   * Parses the next token as an Float value.
   * @param tokenizer The string tokenizer to use for parsing.
   * @throws [CommandSyntaxException] Thrown if the token is not a valid Float value or if the value is not
   * between [min] and [max].
   */
  override fun parse(tokenizer: StringTokenizer) {
    val token =
      tokenizer.next() ?: throw CommandSyntaxException("Expected Float")

    var result =
      token.toFloatOrNull()
        ?: throw CommandSyntaxException("Invalid Float '$token'")

    if (truncationLimit != -1) {
      result = truncate(result, truncationLimit)
    }

    if (result > max) {
      throw CommandSyntaxException("Float must not be more than $max, found $result")
    } else if (result < min) {
      throw CommandSyntaxException("Float must not be less than $min, found $result")
    } else {
      this.parsed = result
    }
  }

  /**
   * Gets a list of suggested Float values based on the next token.
   * @param tokenizer The string tokenizer to use for suggesting values.
   * @return A list of suggested Float values.
   */
  override fun suggestions(tokenizer: StringTokenizer): Iterable<String> =
    SUGGESTIONS

  /**
   * Truncates a Float to a specific limit.
   *
   * ex. 3.158 will truncate down to 3.15 if the limit is 2.
   *
   * @param value The Float that will be truncated.
   * @param limit The truncation limit to cut.
   * @return The truncated Float.
   */
  private fun truncate(value: Float, limit: Int): Float {

    var value = value
    value *= 10.0f.pow(limit)
    value = floor(value)
    value /= 10.0f.pow(limit)
    return value
  }

  companion object {
    private val SUGGESTIONS = listOf("-1.0", "0.0", "1.0")
  }
}