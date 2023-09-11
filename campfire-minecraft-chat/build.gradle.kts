plugins {
  id("campfire.paper-conventions")
  id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
  compileOnly(project(":campfire-minecraft-common"))
  implementation(project(":campfire-minecraft-commands"))
  implementation(project(":campfire-minecraft-commands-paper"))
  implementation(project(":campfire-guice"))
}
