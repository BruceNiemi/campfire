package io.shaded.campfire

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * This class provides the entry point into the monolithic Spring Campfire.
 */
@SpringBootApplication
class CampfireApplication

/**
 * A HOF main() entry point is required for this Spring application.
 */
fun main(args: Array<String>) {
  runApplication<CampfireApplication>(*args)
}
