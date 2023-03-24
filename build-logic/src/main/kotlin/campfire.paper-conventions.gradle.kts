import org.gradle.kotlin.dsl.dependencies

plugins {
  // Apply the common convention plugin for shared build configuration between library and application projects.
  id("campfire.kotlin-common-conventions")

  // Apply the java-library plugin for API and implementation separation.
  `java-library`
}

repositories {
  maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
  compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
}
