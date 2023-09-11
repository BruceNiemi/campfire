package io.shaded.campfire.ui.hud.component

import io.shaded.campfire.ui.hud.Visibility

public abstract class TextComponent(
  public val text: String, visibility: Visibility
) : Component(visibility)
