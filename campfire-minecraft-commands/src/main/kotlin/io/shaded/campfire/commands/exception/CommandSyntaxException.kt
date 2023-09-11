package io.shaded.campfire.commands.exception

/**
 * Exception thrown when there is a syntax error in a command.
 * @param message The error message.
 */
class CommandSyntaxException(override val message: String) : RuntimeException(message)
