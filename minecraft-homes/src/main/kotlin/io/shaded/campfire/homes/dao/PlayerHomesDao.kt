package io.shaded.campfire.homes.dao

import io.shaded.campfire.homes.model.Home
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.jdbi.v3.sqlobject.transaction.Transaction
import java.util.*
import javax.inject.Named

@RegisterConstructorMapper(Home::class)
interface PlayerHomesDao {

  @SqlQuery(
    """
      SELECT
        home_name,
        world_name,
        x,
        y,
        z
      FROM homes
      WHERE player_id = :id
    """
  )
  fun getHomesFromPlayer(@Bind("id") id: UUID): List<Home>

  @SqlQuery(
    """
      SELECT
        home_name
      FROM homes
      WHERE player_id = :id
    """
  )
  fun getHomesNamesFromPlayer(@Bind("id") id: UUID): List<String>

  @SqlUpdate(
    """
      INSERT INTO homes (player_id, home_name, world_name, x, y, z) VALUES
      (:player_id, :home_name, :world_name, :x, :y, :z)
    """
  )
  @Transaction
  fun createHome(
    @Bind("player_id") id: UUID,
    @Bind("home_name") homeName: String,
    @Bind("world_name") worldName: String,
    @Bind("x") x: Double,
    @Bind("y") y: Double,
    @Bind("z") z: Double
  )

  @SqlUpdate(
    """
      DELETE FROM homes
      WHERE player_id = :player_id AND home_name = :home_name
    """
  )
  @Transaction
  fun deleteHome(
    @Named("player_id") id: UUID,
    @Named(":home_name") name: String
  )

}
