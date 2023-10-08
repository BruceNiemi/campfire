package io.shaded.campfire.commands.paper

import io.shaded.campfire.commands.CommandCondition
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

fun requirePlayer(): CommandCondition<CommandSender> =
  { sender, _ ->
    if (sender !is Player) {
      sender.sendMessage(
        Component.text("You must be a player to execute this command.")
          .color(NamedTextColor.RED)
      )
    }

    sender is Player
  }

val NO_PERMISSION = Component.text("You do not have permission to execute this command.")
  .color(NamedTextColor.RED)

fun requirePermission(permission: String): CommandCondition<CommandSender> =
  { sender, _ ->
    println("hello? ${sender.hasPermission(permission)}")
    if (!sender.hasPermission(permission)) {
      sender.sendMessage(NO_PERMISSION)
    }
    sender.hasPermission(permission)
  }
