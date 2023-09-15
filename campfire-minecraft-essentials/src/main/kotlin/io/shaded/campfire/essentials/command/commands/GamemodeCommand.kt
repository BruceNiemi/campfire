package io.shaded.campfire.essentials.command.commands

import io.shaded.campfire.essentials.command.EssentialsCommand
import io.shaded.commands.paper.PaperCommandDispatcher
import io.shaded.commands.paper.arguments.gamemode
import io.shaded.commands.paper.arguments.player
import org.bukkit.GameMode

class GamemodeCommand(
  val commandDispatcher: PaperCommandDispatcher
) : EssentialsCommand() {

  override fun init() {
    this.commandDispatcher.command("gamemode", "gm") {
      val gamemode by gamemode()
      val player by player()

      action {
        player.gameMode = gamemode
      }
    }

    this.commandDispatcher.command("creative", "gmc") {
      val player by player()

      action {
        player.gameMode = GameMode.CREATIVE
      }
    }

    this.commandDispatcher.command("survival", "gms") {
      val player by player()

      action {
        player.gameMode = GameMode.SURVIVAL
      }
    }

    this.commandDispatcher.command("adventure", "gma") {
      val player by player()

      action {
        player.gameMode = GameMode.ADVENTURE
      }
    }

    this.commandDispatcher.command("spectator", "gmsp") {
      val player by player()

      action {
        player.gameMode = GameMode.ADVENTURE
      }
    }
  }
}
