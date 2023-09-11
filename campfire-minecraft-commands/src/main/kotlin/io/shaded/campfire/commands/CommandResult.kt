package io.shaded.campfire.commands

sealed class CommandResult {
  object UnknownCommand : CommandResult()
  object ExecutedCommand : CommandResult()
  object Error : CommandResult()
  object NoBody : CommandResult()
  class FailedParseArguments(val message: String) : CommandResult()
  class FailedPrecondition(val message: String) : CommandResult()
}
