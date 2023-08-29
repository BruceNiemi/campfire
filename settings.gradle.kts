enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

pluginManagement {
  includeBuild("build-logic")

  repositories {
    gradlePluginPortal()
  }
}

rootProject.name = "campfire"

sequenceOf(
  "minecraft-common",
  "minecraft-homes",
  "guice"
).forEach {
  include("campfire-$it")
  project(":campfire-$it").projectDir = file(it)
}
