package io.shaded.campfire.component.gamemode

import io.shaded.campfire.gamemode.CreateGamemodeRequest
import io.shaded.campfire.gamemode.Gamemode
import io.shaded.campfire.gamemode.UpdateGamemodeRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

// We want to follow /networks/{id}/gamemodes/{id}
@RestController
@RequestMapping("/networks/{networkId}/gamemodes")
class GamemodeController(val gamemodeService: GamemodeService) {

  @GetMapping
  fun listGamemodes(@PathVariable networkId: Long): Flux<Gamemode> =
    // todo - Paging
    gamemodeService.findGamemodes(networkId)

  @GetMapping("/{gamemodeId}")
  fun getGamemode(
    @PathVariable networkId: Long, @PathVariable gamemodeId: Long
  ): Mono<Gamemode> =
    gamemodeService.findGamemode(networkId, gamemodeId)

  @GetMapping("/{gamemodeId}")
  fun getGamemode(
    @PathVariable networkId: Long, @PathVariable gamemodeId: String
  ): Mono<Gamemode> =
    gamemodeService.findGamemode(networkId, gamemodeId)

  @PostMapping
  fun createGamemode(
    @PathVariable networkId: Long, @RequestBody request:
    CreateGamemodeRequest
  ): Mono<Gamemode> =
    gamemodeService.createGamemode(
      networkId, request.name, request
        .prettyName, request.description
    )

  @PatchMapping
  fun updateGamemode(
    @PathVariable networkId: Long, @PathVariable gamemodeId: Long,
    @RequestBody request: UpdateGamemodeRequest
  ): Mono<Gamemode> = Mono.empty()

  @DeleteMapping
  fun deleteGamemode(
    @PathVariable networkId: Long,
    @PathVariable gamemodeId: Long
  ): Mono<Gamemode> =
    gamemodeService.deleteGamemode(networkId, gamemodeId)
}
