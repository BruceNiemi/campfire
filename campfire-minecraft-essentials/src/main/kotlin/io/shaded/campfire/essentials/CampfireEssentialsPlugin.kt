package io.shaded.campfire.essentials

import io.shaded.campfire.commands.paper.PaperCampfireCommands
import io.shaded.campfire.commands.paper.arguments.player
import org.bukkit.plugin.java.JavaPlugin

class CampfireEssentialsPlugin : JavaPlugin() {
  override fun onEnable() {
    val paperCampfireCommands = PaperCampfireCommands(this)

    paperCampfireCommands.register("hello") {
      command("hello") {
        command("world") {}

        command("ez"){}
      }

      command("foo") {
        command("bar"){}
      }
    }
  }
}
