package io.shaded.campfire

import java.util.*

/**
 * Proves access to a player.
 */
public interface CampfirePlayer {

  /**
   * The uniquely identifier for a player.
   */
  public val id: UUID

  /**
   * The name of the player
   */
  public val name: String

  /**
   * Send the player a message.
   *
   * @param message either a single message or multiple.
   */
  public fun sendMessage(vararg message: String)
}
