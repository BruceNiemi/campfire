package io.shaded.campfire.player.controller

import io.shaded.campfire.player.model.Player
import io.shaded.campfire.player.model.Session
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/v1/players")
class PlayerController {

  @PostMapping
  fun createPlayer(@RequestBody player: Player): ResponseEntity<Player> {
    return ResponseEntity.status(HttpStatus.CREATED).body(player)
  }

  @GetMapping("/{playerId}")
  fun getPlayer(@PathVariable playerId: UUID):
    ResponseEntity<Player> {
    return ResponseEntity.notFound().build()
  }

  @PutMapping("/{playerId}")
  fun updatePlayer(@PathVariable playerId: UUID, @RequestBody player: Player)
    : ResponseEntity<Player> {
    return ResponseEntity.ok(player)
  }

  @DeleteMapping("/{playerId}")
  fun deletePlayer(@PathVariable playerId: UUID): ResponseEntity<Player> {
    return ResponseEntity.noContent().build()
  }

  @PostMapping("/{playerId}/sessions")
  fun createSession(
    @PathVariable playerId: UUID,
    @RequestBody session: Session
  ): ResponseEntity<Session> {
    return ResponseEntity.status(HttpStatus.CREATED).body(session);
  }

  @GetMapping("/{playerId}/sessions")
  fun getSession(@PathVariable playerId: UUID): ResponseEntity<Session> {
    return ResponseEntity.notFound().build()
  }

  @PutMapping("/{playerId}/sessions")
  fun updateSession(
    @PathVariable playerId: UUID, @RequestBody session:
    Session
  ): ResponseEntity<Session> {
    return ResponseEntity.ok(session)
  }

  @DeleteMapping("/{playerId}/sessions")
  fun deleteSession(@PathVariable playerId: UUID): ResponseEntity<Session> {
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{playerId}/totp")
  fun createTOTP(@PathVariable playerId: UUID): ResponseEntity<String> {
    return ResponseEntity.status(HttpStatus.CREATED).body("")
  }

  @DeleteMapping("/{playerId}/totp")
  fun deleteTOTP(@PathVariable playerId: UUID): ResponseEntity<Unit> {
    return ResponseEntity.noContent().build()
  }

  @PostMapping("/{playerId}/totp/validate")
  fun validateTOTP(
    @PathVariable playerId: UUID,
    @RequestBody totpToken: String
  ): ResponseEntity<Boolean> {
    return ResponseEntity.ok(false)
  }

}
