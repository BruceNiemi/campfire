package io.shaded.campfire.ui.scoreboard

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot
import java.lang.ref.WeakReference

private val ColorCodes = ChatColor.values()
  .map { it.toString() }

/**
 *
 */
abstract class Scoreboard(val plugin: JavaPlugin, player: Player) {

  private val playerReference = WeakReference(player)
  private val scoreboard = Bukkit.getScoreboardManager()
    .newScoreboard
  private val scoreboardObjective =
    scoreboard.registerNewObjective("scoreboard", Criteria.DUMMY, this.title())

  init {
    scoreboardObjective.displaySlot = DisplaySlot.SIDEBAR
    scoreboardObjective.displayName(this.title())

    this.lines()
      .forEachIndexed { index, component ->
        val string = ColorCodes[index]

        scoreboard.registerNewTeam("${index + 1}").apply {
          addEntry(string)
          prefix(component)
        }

        scoreboardObjective.getScore(string).score = index + 1
      }

    Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::update, 2L, 2L);
  }

  private fun update() {
    this.lines()
      .forEachIndexed { index, component ->
        val entry = scoreboard.getTeam("${index + 1}")

        if (entry != null && entry.prefix() !== component) {
          entry.prefix(component)
        }
      }
    playerReference.get()?.scoreboard = scoreboard
  }

  /**
   *
   */
  abstract fun title(): Component

  /**
   *
   */
  abstract fun lines(): List<Component>
}

