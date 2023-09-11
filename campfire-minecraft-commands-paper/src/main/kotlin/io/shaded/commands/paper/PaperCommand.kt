package io.shaded.commands.paper

import io.shaded.campfire.commands.Command
import io.shaded.campfire.commands.CommandResult
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

typealias BukkitCommand = org.bukkit.command.Command

class PaperCommand(
  command: Command<CommandSender>,
  private val commandDispatcher: PaperCommandDispatcher
) : BukkitCommand(
  command.label,
  "",
  "",
  command.aliases
) {

  override fun execute(
    sender: CommandSender,
    commandLabel: String,
    args: Array<String>
  ): Boolean {
    var input = args.joinToString(" ")
    input = this.label + " " + input
    val mappedSender = commandDispatcher.commandSenderMapper.apply(sender)

    when (val result = this.commandDispatcher.execute(input, mappedSender)) {
      is CommandResult.UnknownCommand -> {
        sender.sendMessage("${ChatColor.RED}Unknown command. Type \"/help\" for help.")
      }

      is CommandResult.Error -> {
        sender.sendMessage("${ChatColor.RED}Oops! An error occurred while executing your command.")
      }

      is CommandResult.FailedParseArguments -> {
        sender.sendMessage("${ChatColor.RED}${result.message}")
      }

      is CommandResult.NoBody -> {
        sender.sendMessage("${ChatColor.RED}Oops! An error occurred while executing your command.")
        throw IllegalStateException("Command ${this.label} has no body set for input $input.")
      }

      is CommandResult.FailedPrecondition -> {
        sender.sendMessage("${ChatColor.RED}${result.message}")
      }
      else -> {}
    }
    return true
  }

  override fun tabComplete(
    sender: CommandSender,
    alias: String,
    args: Array<String>
  ): List<String> {
    var input = args.joinToString(" ")
    input = this.label + " " + input
    return this.commandDispatcher.suggest(input)
  }


}
