package io.shaded.campfire.commands.arguments.parser

import io.shaded.campfire.commands.arguments.RequiredArgument
import io.shaded.campfire.commands.exception.CommandSyntaxException
import io.shaded.campfire.commands.exception.NoInputTokenException
import java.util.*
import kotlin.math.floor
import kotlin.math.pow

class FloatArgument<S>(
  name: String,
  private val min: Float = Float.NEGATIVE_INFINITY,
  private val max: Float = Float.POSITIVE_INFINITY,
  private val truncationLimit: Int = -1
) : RequiredArgument<S, Float>(name) {

  init {
    require(truncationLimit == -1 || this.truncationLimit > 0) {
      "Truncation value must be greater than zero"
    }
  }

  override fun parse(sender: S, input: Queue<String>) {
    val token = input.poll() ?: throw NoInputTokenException("Expected Float")

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

  override fun suggest(sender: S, input: List<String>): Iterable<String> =
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
