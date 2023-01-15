package io.shaded.campfire.gamemode

data class CreateGamemodeRequest(
  val name: String,
  val prettyName: String,
  val description: String
)
