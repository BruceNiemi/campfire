package io.shaded.campfire.commands.arguments.parser

import io.shaded.campfire.commands.arguments.RequiredArgument
import io.shaded.campfire.commands.exception.CommandSyntaxException
import io.shaded.campfire.commands.exception.NoInputTokenException
import java.util.*

class LongArgument<S>(
  name: String,
  private val min: Long = Long.MIN_VALUE,
  private val max: Long = Long.MAX_VALUE
) : RequiredArgument<S, Long>(name) {

  override fun parse(sender: S, input: Queue<String>) {
    val token = input.poll() ?: throw NoInputTokenException("Expected Long")

    val long = token.toLongOrNull()
      ?: throw CommandSyntaxException("Invalid Long '$token'")

    if (long > max) {
      throw CommandSyntaxException("Long must not be more than $max, found $long")
    } else if (long < min) {
      throw CommandSyntaxException("Long must not be less than $min, found $long")
    } else {
      this.parsed = long
    }
  }

  override fun suggest(sender: S, input: List<String>): List<String> =
    SUGGESTIONS

  companion object {
    private val SUGGESTIONS = listOf("-1", "0", "1")
  }
}
