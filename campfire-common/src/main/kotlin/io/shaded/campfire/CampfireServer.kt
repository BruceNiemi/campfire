package io.shaded.campfire

import java.util.*

/**
 * Proves access to the underlying server.
 */
public interface CampfireServer {

  /**
   * Retrieves a player on the server or null.
   *
   * @return A player.
   */
  public fun getPlayer(id: UUID): CampfirePlayer?

  /**
   * Retrieves all players that are either connected to the proxy or the
   * specific gamemode.
   *
   * @return A collection of all players.
   */
  public fun getPlayers(): Collection<CampfirePlayer>
}
