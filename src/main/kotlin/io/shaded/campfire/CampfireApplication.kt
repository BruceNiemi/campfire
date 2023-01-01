package io.shaded.campfire

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CampfireApplication

fun main(args: Array<String>) {
	runApplication<CampfireApplication>(*args)
}
