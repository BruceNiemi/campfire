package io.shaded.campfire.gamemode

/**
 * @property id The id of the gamemode
 * @property name The name of the gamemode
 * @property description The description that is displayed to players.
 */
data class Gamemode(
  val id: Long,
  val name: String, // index name no colour codes
  val prettyName: String,
  val description: String
)
