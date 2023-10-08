plugins {
  id("campfire.paper-conventions")
  id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
  implementation(project(":campfire-commands"))
  implementation(project(":campfire-commands-paper"))
}
