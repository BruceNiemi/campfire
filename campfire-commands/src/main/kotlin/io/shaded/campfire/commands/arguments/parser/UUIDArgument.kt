package io.shaded.campfire.commands.arguments.parser

import io.shaded.campfire.commands.arguments.RequiredArgument
import io.shaded.campfire.commands.exception.CommandSyntaxException
import io.shaded.campfire.commands.exception.NoInputTokenException
import java.util.*

class UUIDArgument<S>(name: String) : RequiredArgument<S, UUID>(name) {
  override fun parse(sender: S, input: Queue<String>) {
    val token = input.poll() ?: throw NoInputTokenException("Expected UUID")

    try {
      this.parsed = UUID.fromString(token)
    } catch (_: IllegalArgumentException) {
      throw CommandSyntaxException("Invalid UUID")
    }
  }
}
