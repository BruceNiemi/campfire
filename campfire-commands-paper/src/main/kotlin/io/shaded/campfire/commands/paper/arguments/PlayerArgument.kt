package io.shaded.campfire.commands.paper.arguments

import io.shaded.campfire.commands.arguments.RequiredArgument
import io.shaded.campfire.commands.exception.CommandSyntaxException
import io.shaded.campfire.commands.exception.NoInputTokenException
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class PlayerArgument(name: String) :
  RequiredArgument<CommandSender, Player>(name) {

  override fun parse(sender: CommandSender, input: Queue<String>) {
    val token = input.poll() ?: throw NoInputTokenException("Expected Player")

    val player = Bukkit.getPlayerExact(token)
      ?: throw CommandSyntaxException("No player found for input '$token'.")

    this.parsed = player
  }

  override fun suggest(
    sender: CommandSender,
    input: List<String>
  ): List<String> =
    Bukkit.getOnlinePlayers()
      .map { it.name }
}
