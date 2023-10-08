package io.shaded.campfire.commands.paper

import com.destroystokyo.paper.event.server.AsyncTabCompleteEvent
import io.shaded.campfire.commands.CampfireCommand
import io.shaded.campfire.commands.CampfireCommands
import org.bukkit.command.CommandSender
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class PaperCampfireCommands(private val plugin: JavaPlugin) :
  CampfireCommands<CommandSender>(plugin.name.lowercase()), Listener {

//  init {
//    plugin.server.pluginManager.registerEvents(this, plugin)
//  }

  override fun register(command: CampfireCommand<CommandSender>): Boolean {
    this.commandMap.addCommand(command)

    val commandMap = plugin.server.commandMap
    val paperCommand = PaperCommand(command, this)

    commandMap.knownCommands.remove(command.name)
    command.aliases.forEach { commandMap.knownCommands.remove(it) }


    commandMap.register(
      command.name,
      this.plugin.name.lowercase(),
      paperCommand
    )
    return true
  }

  @EventHandler
  fun asyncTabComplete(event: AsyncTabCompleteEvent) {
    val input =
      if (event.buffer.startsWith('/')) event.buffer.substring(1) else event.buffer

    val inputQueue = tokenize(input)

    if (inputQueue.isEmpty() || commandMap.getCommand(inputQueue[0]) == null) {
      return
    }

    // Add the fancy tool tip soon.
    val suggestions = this.suggestions(event.sender, input)
      .map { AsyncTabCompleteEvent.Completion.completion(it) }
    println(input)
    event.completions().clear()
    event.completions().addAll(suggestions)
  }

}
