package io.shaded.campfire.commands.argument.parser

import io.shaded.campfire.commands.StringTokenizer
import io.shaded.campfire.commands.argument.CommandArgument
import io.shaded.campfire.commands.exception.CommandSyntaxException
import java.util.*

/**
 * A command argument that represents a UUID value.
 */
class UUIDArgument : CommandArgument<UUID>() {
  override fun parse(tokenizer: StringTokenizer) {
    val token = tokenizer.next() ?: throw CommandSyntaxException("Expected UUID")

    try {
      this.parsed = UUID.fromString(token)
    } catch (_: IllegalArgumentException) {
      throw CommandSyntaxException("Invalid UUID")
    }
  }
}
