package io.shaded.campfire.commands.argument.parser

import io.shaded.campfire.commands.StringTokenizer
import io.shaded.campfire.commands.argument.CommandArgument
import io.shaded.campfire.commands.exception.CommandSyntaxException

/**
 * Represents a command argument that takes a single word or a greedy phrase as its value.
 *
 * @param type the type of string argument
 */
class StringArgument(private val type: StringType) : CommandArgument<String>() {

  /**
   * An enum that defines the type of string argument that can be parsed by this [StringArgument].
   */
  enum class StringType {
    // Represents a single word argument
    SINGLE_WORD,

    // Represents a greedy phrase argument that consumes all the remaining input tokens
    GREEDY_PHRASE
  }

  /**
   * Parses the next token in the input [tokenizer] according to the type of string argument
   * specified during initialization of this [StringArgument].
   *
   * @throws CommandSyntaxException if the input is invalid or incomplete
   */
  override fun parse(tokenizer: StringTokenizer) {
    if (type == StringType.SINGLE_WORD) {
      // If the argument type is single word, parse the next token as a single word
      this.parsed = tokenizer.next() ?: throw CommandSyntaxException("Expected Word")
    } else {
      // If the argument type is greedy phrase, consume all the remaining tokens and parse them as a single phrase
      val stringBuilder = StringBuilder()

      while (tokenizer.hasNext()) {
        stringBuilder.append(" ")
        stringBuilder.append(tokenizer.next())
      }

      this.parsed = stringBuilder.toString().trim()
    }
  }
}
