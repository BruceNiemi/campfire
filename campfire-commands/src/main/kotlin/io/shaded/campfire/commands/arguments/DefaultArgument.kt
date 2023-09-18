package io.shaded.campfire.commands.arguments

typealias DefaultArgumentSupplier<S, T> = (sender: S) -> T

abstract class DefaultArgument<S, T : Any>(
  name: String,
  val supplier: DefaultArgumentSupplier<S, T>
) : Argument<S, T>(name) {

  override lateinit var parsed: T
}
