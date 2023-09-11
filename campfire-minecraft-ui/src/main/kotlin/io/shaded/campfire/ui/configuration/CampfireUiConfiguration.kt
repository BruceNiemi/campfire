package io.shaded.campfire.ui.configuration

import io.shaded.campfire.ui.annotation.CampfireUiDsl
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 *
 */
public data class CampfireUiConfiguration(
  val scoreboard: ScoreboardSettings = ScoreboardSettings()
) {

  @CampfireUiDsl
  public inline fun scoreboard(builder: ScoreboardSettings.() -> Unit) {
    scoreboard.apply(builder)
  }
}

/**
 *
 */
public data class ScoreboardSettings(
  var updateDelay: Duration = 5.seconds
)
