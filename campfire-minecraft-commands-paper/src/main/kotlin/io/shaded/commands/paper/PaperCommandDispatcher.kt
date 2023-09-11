package io.shaded.commands.paper

import io.shaded.campfire.commands.Command
import io.shaded.campfire.commands.CommandDispatcher
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.util.function.Function

class PaperCommandDispatcher(
  val plugin: JavaPlugin,
  val commandSenderMapper: Function<CommandSender, CommandSender>
) : CommandDispatcher<CommandSender>() {

  override fun register(command: Command<CommandSender>) {
    command.aliases.add("${plugin.name.lowercase()}:${command.label}")
    val paperCommand = PaperCommand(
      command,
      this
    )
    val commandMap = this.plugin.server.commandMap

    commandMap.knownCommands.remove(command.label)
    command.aliases.forEach { commandMap.knownCommands.remove(it) }

    commandMap.register(
      command.label,
      this.plugin.name.lowercase(),
      paperCommand
    )
  }

}
