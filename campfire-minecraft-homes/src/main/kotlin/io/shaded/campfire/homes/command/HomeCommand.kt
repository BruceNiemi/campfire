package io.shaded.campfire.homes.command

import com.google.inject.Inject
import io.shaded.campfire.homes.service.HomeService
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class HomeCommand @Inject constructor(private val homeService: HomeService) :
  CommandExecutor, TabCompleter {

  override fun onCommand(
    sender: CommandSender,
    command: Command,
    label: String,
    args: Array<String>
  ): Boolean {
    if (sender !is Player) return false

    // Later we will open a menu that aggerates the tab complete data and
    // displays it to the player.
    if (args.isEmpty()) {
      sender.sendMessage("Command usage: /home <name>")
      return true
    }

    val homeName = args[0]

    when (this.homeService.teleport(sender, homeName)) {
      HomeService.TeleportResult.NotFound ->
        sender.sendMessage("Oops, you don't have a home with that name!")
      HomeService.TeleportResult.WorldNotLoaded ->
        sender.sendMessage(
          "Oops, there was a hiccup while teleporting you to your home. Please try again later!"
        )
      HomeService.TeleportResult.Teleported -> {/* NO-OP*/ }
    }

    return true
  }

  override fun onTabComplete(
    sender: CommandSender,
    command: Command,
    label: String,
    args: Array<String>
  ): List<String> {
    if (sender !is Player) return emptyList()

    if (args.size == 1) {
      return this.homeService.listPlayerHomes(sender)
    }

    return emptyList()
  }

}
