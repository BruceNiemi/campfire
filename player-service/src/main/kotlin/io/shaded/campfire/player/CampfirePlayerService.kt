package io.shaded.campfire.player

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CampfirePlayerService

fun main(args: Array<String>) {
  runApplication<CampfirePlayerService>(*args)
}
