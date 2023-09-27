package io.shaded.campfire.commands.arguments.parser

import io.shaded.campfire.commands.arguments.RequiredArgument
import io.shaded.campfire.commands.exception.NoInputTokenException
import java.util.*

class StringArgument<S>(name: String, val type: StringType) :
  RequiredArgument<S, String>(name) {

  /**
   * An enum that defines the type of string argument that can be parsed by this [StringArgument].
   */
  enum class StringType {
    // Represents a single word argument
    SINGLE_WORD,

    // Represents a greedy phrase argument that consumes all the remaining input tokens
    GREEDY_PHRASE
  }

  override fun parse(sender: S, input: Queue<String>) {
    if (type == StringType.SINGLE_WORD) {
      // If the argument type is single word, parse the next token as a single word
      this.parsed = input.poll() ?: throw NoInputTokenException("Expected Word")
    } else {
      // If the argument type is greedy phrase, consume all the remaining tokens and parse them as a single phrase
      val stringBuilder = StringBuilder()

      while (!input.isEmpty()) {
        stringBuilder.append(" ")
        stringBuilder.append(input.poll())
      }

      parsed = stringBuilder.toString().trim()
    }
  }
}
