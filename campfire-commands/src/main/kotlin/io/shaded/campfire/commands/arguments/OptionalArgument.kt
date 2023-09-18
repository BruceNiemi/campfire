package io.shaded.campfire.commands.arguments

abstract class OptionalArgument<S, T : Any>(name: String) : Argument<S, T?>(name) {
  override var parsed: T? = null
}
