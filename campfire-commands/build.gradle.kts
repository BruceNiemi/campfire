plugins {
  id("campfire.kotlin-library-conventions")
}

dependencies {
  implementation(kotlin("reflect"))

  testImplementation(kotlin("test"))
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
}

tasks.withType<Test> {
  useJUnitPlatform()
}
