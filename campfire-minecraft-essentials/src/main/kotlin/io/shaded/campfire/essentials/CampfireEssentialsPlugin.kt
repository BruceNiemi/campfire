package io.shaded.campfire.essentials

import io.shaded.campfire.essentials.command.commands.GamemodeCommand
import io.shaded.commands.paper.PaperCommandDispatcher
import org.bukkit.plugin.java.JavaPlugin
import java.util.function.Function

/**
 *
 */
class CampfireEssentialsPlugin : JavaPlugin() {
  override fun onEnable() {
    val dispatcher = PaperCommandDispatcher(this, Function.identity())

    GamemodeCommand(dispatcher).init()
  }
}
