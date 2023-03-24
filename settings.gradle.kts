enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

pluginManagement {
  includeBuild("build-logic")

  repositories {
    gradlePluginPortal()
  }
}

rootProject.name = "campfire"

sequenceOf(
  "server",
  "bukkit",
  "velocity"
).forEach {
  include("campfire-$it")
  project(":campfire-$it").projectDir = file(it)
}
