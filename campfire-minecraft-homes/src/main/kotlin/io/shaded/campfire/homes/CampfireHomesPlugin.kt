package io.shaded.campfire.homes

import com.google.inject.Guice
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.shaded.campfire.homes.command.DeleteHomeCommand
import io.shaded.campfire.homes.command.HomeCommand
import io.shaded.campfire.homes.command.SetHomeCommand
import io.shaded.campfire.homes.dao.PlayerHomesDao
import io.shaded.campfire.homes.inject.CampfireModule
import org.bukkit.plugin.java.JavaPlugin
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.core.kotlin.withExtensionUnchecked
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
      driverClassName = org.postgresql.Driver::class.qualifiedName
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

    jdbi.withExtensionUnchecked(PlayerHomesDao::class) {
      it.createTable()
    }

    val injector = Guice.createInjector(CampfireModule(this, jdbi))

    // This is null safe as we've defined the commands in the plugin.yml.
    this.getCommand("home")?.setExecutor(
      injector.getInstance(HomeCommand::class.java)
    )
    this.getCommand("home")?.tabCompleter =
      injector.getInstance(HomeCommand::class.java)

    this.getCommand("sethome")?.setExecutor(
      injector.getInstance(SetHomeCommand::class.java)
    )

    this.getCommand("delhome")?.setExecutor(
      injector.getInstance(DeleteHomeCommand::class.java)
    )
    this.getCommand("delhome")?.tabCompleter =
      injector.getInstance(DeleteHomeCommand::class.java)
  }

  override fun onDisable() {
    if (this::hikariDataSource.isInitialized) {
      this.hikariDataSource.close()
    }
  }
}
