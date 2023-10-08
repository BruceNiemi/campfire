enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

pluginManagement {
  includeBuild("build-logic")

  repositories {
    gradlePluginPortal()
  }
}

rootProject.name = "campfire"

include(
  ":campfire-minecraft-common", ":campfire-guice", ":campfire-minecraft-ui",
  ":campfire-minecraft-homes", ":campfire-minecraft-commands",
  ":campfire-minecraft-commands-paper", ":campfire-minecraft-essentials",
  ":campfire-commands", ":campfire-commands-paper"
)
