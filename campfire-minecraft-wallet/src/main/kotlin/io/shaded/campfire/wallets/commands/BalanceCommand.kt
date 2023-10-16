package io.shaded.campfire.wallets.commands

import io.shaded.campfire.wallets.service.WalletService
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class BalanceCommand(val walletService: WalletService) : CommandExecutor, TabCompleter {
  override fun onCommand(
    sender: CommandSender,
    command: Command,
    label: String,
    args: Array<String>
  ): Boolean {
    if (sender !is Player) return false

    if(args.isEmpty()) {

    } else if(args.size > 1){

    }

    return true
  }

  override fun onTabComplete(
    sender: CommandSender,
    command: Command,
    label: String,
    args: Array<String>
  ): List<String>? {
    TODO("Not yet implemented")
  }
}
