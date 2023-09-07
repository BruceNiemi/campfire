package io.shaded.campfire.homes.command

import com.google.inject.Inject
import io.shaded.campfire.homes.service.HomeService
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class DeleteHomeCommand @Inject constructor(val homeService: HomeService) :
  CommandExecutor, TabCompleter {

  override fun onCommand(
    sender: CommandSender,
    command: Command,
    label: String,
    args: Array<String>
  ): Boolean {
    if (sender !is Player) return false

    if (args.isEmpty()) {
      sender.sendMessage("Command usage: /delhome <name>")
      return true
    }

    val homeName = args[0]

    this.homeService.deleteHome(sender, homeName)

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
