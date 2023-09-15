package io.shaded.commands.paper.arguments

import io.shaded.campfire.commands.Command
import io.shaded.campfire.commands.StringTokenizer
import io.shaded.campfire.commands.argument.CommandArgument
import io.shaded.campfire.commands.exception.CommandSyntaxException
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PlayerArgument : CommandArgument<Player>() {
  override fun parse(tokenizer: StringTokenizer) {
    val token =
      tokenizer.next() ?: throw CommandSyntaxException("Expected username")

    val player = Bukkit.getPlayerExact(token)
      ?: throw CommandSyntaxException("Player with name $token is offline.")
    this.parsed = player
  }

  override fun suggestions(tokenizer: StringTokenizer): Iterable<String> =
    Bukkit.getOnlinePlayers().map { it.name }
}

fun Command<CommandSender>.player(): CommandArgument<Player> {
  val playerArg = PlayerArgument()
  this.arguments.add(playerArg)
  return playerArg
}
