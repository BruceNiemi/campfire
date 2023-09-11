package io.shaded.campfire.ui.hud

import kotlin.time.Duration

public sealed class Visibility {
  public object Permanent : Visibility()
  public data class Timed(val duration: Duration) : Visibility()
}
