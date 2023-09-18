package io.shaded.campfire.commands.arguments.parser

import io.shaded.campfire.commands.arguments.RequiredArgument
import io.shaded.campfire.commands.exception.CommandSyntaxException
import io.shaded.campfire.commands.exception.NoInputTokenException
import java.util.*

class IntegerArgument<S>(
  name: String,
  private val min: Int = Int.MIN_VALUE,
  private val max: Int = Int.MAX_VALUE
) : RequiredArgument<S, Int>(name) {

  override fun parse(sender: S, input: Queue<String>) {
    val token = input.poll() ?: throw NoInputTokenException("Expected Integer")

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
  override fun suggest(sender: S, input: List<String>): Iterable<String> =
    SUGGESTIONS

  companion object {
    private val SUGGESTIONS = listOf("-1", "0", "1")
  }
}
