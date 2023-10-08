package io.shaded.campfire.commands.paper.arguments

import io.shaded.campfire.commands.CampfireCommand
import io.shaded.campfire.commands.arguments.*
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

fun CampfireCommand<CommandSender>.player(name: String): RequiredArgument<CommandSender, Player> {
  val argument = PlayerArgument(name)
  this.arguments.add(argument)
  return argument
}

fun CampfireCommand<CommandSender>.player(
  name: String,
  supplier: DefaultArgumentSupplier<CommandSender, Player>
): DefaultArgumentAdapter<CommandSender, Player> {
  val argument = PlayerArgument(name).default(supplier)
  this.arguments.add(argument)
  return argument
}

fun CampfireCommand<CommandSender>.optionalPlayer(
  name: String
): OptionalAdapter<CommandSender, Player> {
  val argument = PlayerArgument(name).optional()
  this.arguments.addAll(arguments)
  return argument
}
