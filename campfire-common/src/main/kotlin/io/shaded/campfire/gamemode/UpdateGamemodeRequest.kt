package io.shaded.campfire.gamemode

data class UpdateGamemodeRequest(
  val name: String,
  val description: String? = null
)
