package io.shaded.campfire.homes.model

import org.jdbi.v3.core.mapper.reflect.ColumnName

data class Home(
  @ColumnName("home_name") val name: String,
  @ColumnName("world_name") val world: String,
  @ColumnName("x") val x: Double,
  @ColumnName("y") val y: Double,
  @ColumnName("z") val z: Double,
  @ColumnName("pitch") val pitch: Double,
  @ColumnName("yaw") val yaw: Double
)

