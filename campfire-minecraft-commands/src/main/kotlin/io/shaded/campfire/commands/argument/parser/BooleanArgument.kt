package io.shaded.campfire.commands.argument.parser

import io.shaded.campfire.commands.StringTokenizer
import io.shaded.campfire.commands.argument.CommandArgument
import io.shaded.campfire.commands.exception.CommandSyntaxException

/**
 * A command argument that represents a boolean value.
 */
class BooleanArgument : CommandArgument<Boolean>() {

  /**
   * Parses the next token as a boolean value.
   * @param tokenizer The string tokenizer to use for parsing.
   * @throws [CommandSyntaxException] Thrown if the token is not a valid boolean value.
   */
  override fun parse(tokenizer: StringTokenizer) {
    val token = tokenizer.next() ?: throw CommandSyntaxException("Expected boolean")
    if (token.equals("true", true)) {
      this.parsed = true
    } else if (token.equals("false", true)) {
      this.parsed = false
    } else {
      throw CommandSyntaxException("Invalid boolean, expected true or false but found '$token'.")
    }
  }

  /**
   * Gets a list of suggested boolean values based on the next token.
   * @param tokenizer The string tokenizer to use for suggesting values.
   * @return A list of suggested boolean values.
   */
  override fun suggestions(tokenizer: StringTokenizer): Iterable<String> = SUGGESTIONS

  companion object {
    private val SUGGESTIONS = listOf("true", "false")
  }
}
