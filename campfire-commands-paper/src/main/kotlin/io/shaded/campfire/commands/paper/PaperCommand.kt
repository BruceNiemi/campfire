package io.shaded.campfire.commands.paper

import io.shaded.campfire.commands.CampfireCommand
import io.shaded.campfire.commands.CampfireCommands
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

typealias BukkitCommand = Command

class PaperCommand(
  val command: CampfireCommand<CommandSender>,
  val campfireCommands: PaperCampfireCommands
) : BukkitCommand(command.name, "", "", command.aliases.asList()) {

  override fun execute(
    sender: CommandSender,
    commandLabel: String,
    args: Array<String>
  ): Boolean {
    val input = commandLabel + " " + args.joinToString(" ")

    when (val result = campfireCommands.dispatch(sender, input)) {
      is CampfireCommands.CommandExecutionResult.EXECUTED -> {}
      is CampfireCommands.CommandExecutionResult.CANCELLED -> {}
      is CampfireCommands.CommandExecutionResult.UNKNOWN -> {
        sender.sendMessage("Unknown command, Type '/help' for help.")
      }
      is CampfireCommands.CommandExecutionResult.PRECONDITION_FAILED -> {
        return false
      }
      is CampfireCommands.CommandExecutionResult.EXECUTOR_EXCEPTION -> {
        sender.sendMessage(
          "An error has occurred when executing the command, try again later."
        )
      }
      is CampfireCommands.CommandExecutionResult.INVALID_SYNTAX -> {
        sender.sendMessage(
          result.message
        )
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
    val suggeested = this.campfireCommands.suggestions(sender, input)
    println(args)
    println(suggeested)
    return suggeested
  }
}
