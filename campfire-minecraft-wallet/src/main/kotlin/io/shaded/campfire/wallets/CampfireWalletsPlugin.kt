package io.shaded.campfire.wallets

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.shaded.campfire.wallets.service.CurrencyService
import io.shaded.campfire.wallets.service.WalletService
import org.bukkit.plugin.java.JavaPlugin
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import java.sql.DriverManager
import java.util.*

class CampfireWalletsPlugin : JavaPlugin() {
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
      maximumPoolSize = 2
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
        CREATE TABLE IF NOT EXISTS currencies (
            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
            name VARCHAR(255) NOT NULL UNIQUE,
            symbol VARCHAR(10) NOT NULL UNIQUE,
            description VARCHAR(255)
        );

        CREATE TABLE IF NOT EXISTS wallets (
            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
            player_id UUID NOT NULL,
            currency_id UUID NOT NULL,
            balance NUMERIC(38, 0) DEFAULT 0 CHECK (balance >= 0),
            FOREIGN KEY (currency_id) REFERENCES currencies (id),
            UNIQUE(player_id, currency_id)
        );
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
