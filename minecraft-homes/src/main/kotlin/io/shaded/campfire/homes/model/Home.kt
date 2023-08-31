package io.shaded.campfire.homes.model

import java.sql.Date

data class Home(
  val name: String,
  val world: String,
  val x: Double,
  val y: Double,
  val z: Double,
  val createTime: Date
)

