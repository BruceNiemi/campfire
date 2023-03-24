import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.`java-library`
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories

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
  compileOnly("com.velocitypowered:velocity-api:3.1.1")
  annotationProcessor("com.velocitypowered:velocity-api:3.1.1")
}
