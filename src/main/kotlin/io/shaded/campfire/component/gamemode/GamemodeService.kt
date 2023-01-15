package io.shaded.campfire.component.gamemode

import io.shaded.campfire.gamemode.Gamemode
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface GamemodeService {
  fun createGamemode(
    network: Long, name: String, prettyName: String, description: String
  ): Mono<Gamemode>

  fun findGamemodes(network: Long): Flux<Gamemode>

  fun findGamemode(network: Long, id: Long): Mono<Gamemode>

  fun findGamemode(network: Long, name: String): Mono<Gamemode>

  fun updateGamemode(network: Long, gamemode: Gamemode): Mono<Gamemode>

  fun deleteGamemode(network: Long, id: Long) : Mono<Gamemode>
}
