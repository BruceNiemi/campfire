package io.shaded.campfire.commands.exception

class CommandNotFoundException(override val message: String) :
  RuntimeException(message)
