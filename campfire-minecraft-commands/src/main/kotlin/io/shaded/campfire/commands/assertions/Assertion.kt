package io.shaded.campfire.commands.assertions

import java.util.function.Predicate

/**
 * Wraps a predicate while also providing context to the sender
 * if the condition was failed.
 */
interface Assertion<S> : Predicate<S> {

  /**
   * A message that is sent to the sender if the precondition is not met.
   *
   * @param sender The sender that has sent the command
   * @return The message when the command has failed.
   */
  fun failed(sender: S): String = DEFAULT_ERROR

  companion object {
    private const val DEFAULT_ERROR = "Oops! An error occurred while executing your command."
  }

}
