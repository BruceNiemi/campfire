package io.shaded.commands.paper.arguments

import io.shaded.campfire.commands.Command
import io.shaded.campfire.commands.StringTokenizer
import io.shaded.campfire.commands.argument.CommandArgument
import io.shaded.campfire.commands.exception.CommandSyntaxException
import org.bukkit.GameMode
import org.bukkit.command.CommandSender

class GamemodeArgument : CommandArgument<GameMode>() {
  override fun parse(tokenizer: StringTokenizer) {
    val token =
      tokenizer.next() ?: throw CommandSyntaxException("Expected gamemode")

    return when (token.lowercase()) {
      "creative" -> this.parsed = GameMode.CREATIVE
      "1" -> this.parsed = GameMode.CREATIVE
      "survival" -> this.parsed = GameMode.SURVIVAL
      "0" -> this.parsed = GameMode.SURVIVAL
      "adventure" -> this.parsed = GameMode.ADVENTURE
      "2" -> this.parsed = GameMode.ADVENTURE
      "spectator" -> this.parsed = GameMode.SPECTATOR
      "3" -> this.parsed = GameMode.SPECTATOR
      else -> throw CommandSyntaxException("Invalid gamemode '$token'")
    }
  }

  override fun suggestions(tokenizer: StringTokenizer): Iterable<String> {
    return SUGGESTIONS
  }

  companion object {
    val SUGGESTIONS = listOf(
      "creative",
      "survival",
      "adventure",
      "spectator",
      "1",
      "0",
      "2",
      "3"
    )
  }
}

fun Command<CommandSender>.gamemode(): CommandArgument<GameMode> {
  val gamemodeArg = GamemodeArgument()
  this.arguments.add(gamemodeArg)
  return gamemodeArg
}
