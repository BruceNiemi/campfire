import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.1"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
}

group = "io.shaded"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
  implementation(project(":campfire-common"))
  implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
  implementation("io.lettuce:lettuce-core:6.2.2.RELEASE")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}


tasks {
  bootJar {
    launchScript()
  }

  // From StackOverflow: https://stackoverflow.com/a/53087407
  // Licensed under: CC BY-SA 4.0
  // Adapted to Kotlin
  register<Copy>("buildForDocker") {
    from(bootJar)
    into("build/libs/docker")
    rename { fileName ->
      // a simple way is to remove the "-$version" from the jar filename
      // but you can customize the filename replacement rule as you wish.
      fileName.replace("-$version", "")
    }
  }
}
