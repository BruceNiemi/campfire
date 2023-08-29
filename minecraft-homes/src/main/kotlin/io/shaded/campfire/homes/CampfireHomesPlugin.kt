package io.shaded.campfire.homes

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.bukkit.plugin.java.JavaPlugin
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import java.sql.DriverManager


/**
 * Plugin that allows players to store a set of locations in which they can
 * teleport to if and only if the location is safe to teleport to. The
 * player is only allowed to have a fixed number of homes based upon their
 * permission(s) or role where home will always be the maximal value.
 */
class CampfireHomesPlugin : JavaPlugin() {
  private lateinit var hikariDataSource: HikariDataSource

  override fun onEnable() {
    DriverManager.registerDriver(org.postgresql.Driver())

    // Test configuration that will be moved into a proper configuration
    // system, but this works for the time being.
    val hikariConfig = HikariConfig().apply {
      driverClassName = org.postgresql.Driver::javaClass.name
      jdbcUrl = "jdbc:postgresql://localhost:5432/campfire"
      username = "campfire"
      password = "campfire"
      maximumPoolSize = 10
    }

    this.hikariDataSource = HikariDataSource(hikariConfig)

    val jdbi = Jdbi.create(this.hikariDataSource)
      .installPlugin(SqlObjectPlugin())
      .installPlugin(KotlinSqlObjectPlugin())
      .installPlugin(KotlinPlugin())
      .installPlugin(PostgresPlugin())

    jdbi.useTransaction<java.lang.Exception> {
      it.execute(
        """
          CREATE TABLE IF NOT EXISTS homes (
            id          SERIAL PRIMARY KEY,
            player_id   UUID NOT NULL,
            home_name   VARCHAR(36) NOT NULL,
            world_name  VARCHAR(36) NOT NULL,
            x           DOUBLE PRECISION NOT NULL,
            y           DOUBLE PRECISION NOT NULL,
            z           DOUBLE PRECISION NOT NULL,

            UNIQUE(player_id, home_name)
          )
        """.trimIndent()
      )
    }
  }

  override fun onDisable() {
    if (this::hikariDataSource.isInitialized) {
      this.hikariDataSource.close()
    }
  }
}
