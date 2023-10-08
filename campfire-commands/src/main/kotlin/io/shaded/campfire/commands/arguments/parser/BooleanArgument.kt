package io.shaded.campfire.commands.arguments.parser

import io.shaded.campfire.commands.arguments.RequiredArgument
import io.shaded.campfire.commands.exception.CommandSyntaxException
import io.shaded.campfire.commands.exception.NoInputTokenException
import java.util.*

class BooleanArgument<S>(name: String) : RequiredArgument<S, Boolean>(name) {

  override fun parse(sender: S, input: Queue<String>) {
    val token = input.poll() ?: throw NoInputTokenException("Expected boolean")

    if (token.equals("true", true)) {
      this.parsed = true
    } else if (token.equals("false", true)) {
      this.parsed = false
    } else {
      throw CommandSyntaxException("Invalid boolean, expected true or false but found '$token'.")
    }
  }

  override fun suggest(sender: S, input: List<String>): List<String> =
    SUGGESTIONS

  companion object {
    private val SUGGESTIONS = listOf("true", "false")
  }
}
