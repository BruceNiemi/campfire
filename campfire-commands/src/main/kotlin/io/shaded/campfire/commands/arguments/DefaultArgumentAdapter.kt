package io.shaded.campfire.commands.arguments

import io.shaded.campfire.commands.exception.NoInputTokenException
import java.util.*

class DefaultArgumentAdapter<S, T : Any>(
  supplier: DefaultArgumentSupplier<S, T>,
  private val argument: RequiredArgument<S, T>
) : DefaultArgument<S, T>(argument.name, supplier) {

  override fun parse(sender: S, input: Queue<String>) {
    try {
      argument.parse(sender, input)

      this.parsed = argument.parsed
    } catch (_: NoInputTokenException) {
      this.parsed = supplier.invoke(sender)
    }
  }

  override fun suggest(sender: S, input: List<String>): Iterable<String> {
    return this.argument.suggest(sender, input)
  }
}
