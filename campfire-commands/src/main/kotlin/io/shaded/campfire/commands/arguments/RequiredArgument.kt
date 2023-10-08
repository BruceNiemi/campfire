package io.shaded.campfire.commands.arguments

abstract class RequiredArgument<S, T : Any>(name: String) :
  Argument<S, T>(name) {

  override lateinit var parsed: T
}
