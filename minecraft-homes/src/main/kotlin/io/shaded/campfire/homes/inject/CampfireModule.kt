package io.shaded.campfire.homes.inject

import com.google.inject.Provides
import com.google.inject.Singleton
import io.shaded.campfire.guice.inject.KAbstractModule
import io.shaded.campfire.homes.dao.PlayerHomesDao
import org.bukkit.plugin.java.JavaPlugin
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.kotlin.onDemand

class CampfireModule(private val plugin: JavaPlugin, private val jdbi: Jdbi) :
  KAbstractModule() {

  override fun configure() {
    bind<JavaPlugin>().toInstance(this.plugin)

    bind<Jdbi>().toInstance(this.jdbi)
  }

  @Provides
  @Singleton
  fun provideHomesDao(jdbi: Jdbi): PlayerHomesDao =
    jdbi.onDemand(PlayerHomesDao::class)
}
