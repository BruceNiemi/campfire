plugins {
  id("campfire.paper-conventions")
  id("com.github.johnrengelman.shadow") version "8.1.1"
}

dependencies {
  compileOnly(project(":campfire-minecraft-common"))
  implementation(platform("org.jdbi:jdbi3-bom:3.41.1-SNAPSHOT"))
  implementation("org.jdbi:jdbi3-core")
  implementation("org.jdbi:jdbi3-sqlobject")
  implementation("org.jdbi:jdbi3-kotlin")
  implementation("org.jdbi:jdbi3-kotlin-sqlobject")
  implementation("org.jdbi:jdbi3-postgres")
  implementation("org.postgresql:postgresql:42.6.0")
  implementation("com.zaxxer:HikariCP:5.0.1")
  implementation(project(":campfire-guice"))
}
