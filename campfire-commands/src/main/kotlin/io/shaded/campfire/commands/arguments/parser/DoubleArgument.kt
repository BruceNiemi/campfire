package io.shaded.campfire.commands.arguments.parser

import io.shaded.campfire.commands.arguments.RequiredArgument
import io.shaded.campfire.commands.exception.CommandSyntaxException
import io.shaded.campfire.commands.exception.NoInputTokenException
import java.util.*
import kotlin.math.floor
import kotlin.math.pow

class DoubleArgument<S>(
  name: String,
  private val min: Double = Double.NEGATIVE_INFINITY,
  private val max: Double = Double.POSITIVE_INFINITY,
  private val truncationLimit: Int = -1
) : RequiredArgument<S, Double>(name) {

  init {
    require(truncationLimit == -1 || this.truncationLimit > 0) {
      "Truncation value must be greater than zero"
    }
  }

  override fun parse(sender: S, input: Queue<String>) {
    val token = input.poll() ?: throw NoInputTokenException("Expected Double")

    var result =
      token.toDoubleOrNull()
        ?: throw CommandSyntaxException("Invalid Double '$token'")

    if (truncationLimit != -1) {
      result = truncate(result, truncationLimit)
    }

    if (result > max) {
      throw CommandSyntaxException("Double must not be more than $max, found $result")
    } else if (result < min) {
      throw CommandSyntaxException("Double must not be less than $min, found $result")
    } else {
      this.parsed = result
    }
  }

  override fun suggest(sender: S, input: List<String>): Iterable<String> =
    SUGGESTIONS

  /**
   * Truncates a double to a specific limit.
   *
   * ex. 3.158 will truncate down to 3.15 if the limit is 2.
   *
   * @param value The double that will be truncated.
   * @param limit The truncation limit to cut.
   * @return The truncated double.
   */
  private fun truncate(value: Double, limit: Int): Double {
    var value = value
    value *= 10.0.pow(limit.toDouble())
    value = floor(value)
    value /= 10.0.pow(limit.toDouble())
    return value
  }

  companion object {
    private val SUGGESTIONS = listOf("-1.0", "0.0", "1.0")
  }
}
