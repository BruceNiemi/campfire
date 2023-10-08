package io.shaded.campfire.commands.arguments

import io.shaded.campfire.commands.exception.NoInputTokenException
import java.util.*

class OptionalAdapter<S, T : Any>(
  private val argument: RequiredArgument<S, T>
) : OptionalArgument<S, T>(argument.name) {

  override fun parse(sender: S, input: Queue<String>) {
    try {
      argument.parse(sender, input)

      this.parsed = argument.parsed
    } catch (_: NoInputTokenException) {

    }
  }

  override fun suggest(sender: S, input: List<String>): List<String> {
    return this.argument.suggest(sender, input)
  }
}
