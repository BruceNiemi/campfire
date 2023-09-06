package io.shaded.campfire.homes.service

import com.google.inject.Inject
import io.shaded.campfire.homes.dao.PlayerHomesDao
import io.shaded.campfire.homes.model.Home
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.jdbi.v3.sqlobject.transaction.Transaction

class HomeService @Inject constructor(private val playerHomesDao: PlayerHomesDao) {

  sealed class CreateHomeResult {
    object Created : CreateHomeResult()
    object DuplicateName : CreateHomeResult()
    object UnAllowedWorld : CreateHomeResult()
    class MaximumHomes(val current: Int) : CreateHomeResult()
    object Error : CreateHomeResult()
  }

  @Transaction
  fun createHome(
    player: Player,
    name: String,
    location: Location
  ): CreateHomeResult {

    // Some things that need to happen before a home can be created for a
    // player we firstly need to check if the player is attempting to create
    // a home in a world that is not allowed. Then we need to check if the
    // player is going to exceed home limit. Then we can write the home to
    // the database iff there is not a duplicate home name then we can write
    // and publish an event.

    this.playerHomesDao.createHome(
      player.uniqueId,
      name,
      location.world.name,
      location.x,
      location.y,
      location.z
    )

    return CreateHomeResult.Created
  }

  @Transaction
  fun deleteHome(player: Player, name: String) {
    this.playerHomesDao.deleteHome(player.uniqueId, name)
  }

  sealed class TeleportResult {
    object Teleported : TeleportResult()
    object WorldNotLoaded : TeleportResult()
    object NotFound : TeleportResult()
  }

  fun teleport(player: Player, name: String): TeleportResult {
    val home = this.playerHomesDao.getHome(player.uniqueId, name)
      ?: return TeleportResult.NotFound
    return this.teleport(player, home)
  }

  fun teleport(player: Player, home: Home): TeleportResult {
    val world =
      Bukkit.getWorld(home.world) ?: return TeleportResult.WorldNotLoaded

    // Reading over the implementation of the teleport we can get away with
    // simply ignoring the result of teleport/teleportAsync as only returns
    // false for some edge cases. If later down the line there are errors
    // with this function we can simply await the result of the teleport and
    // properly handle the errors further to the player.
    player.teleportAsync(Location(world, home.x, home.y, home.z))

    return TeleportResult.Teleported
  }

}
