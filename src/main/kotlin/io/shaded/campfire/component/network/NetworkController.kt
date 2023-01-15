package io.shaded.campfire.component.network

import io.shaded.campfire.component.network.service.NetworkService
import io.shaded.campfire.network.CreateNetworkRequest
import io.shaded.campfire.network.Network
import io.shaded.campfire.network.UpdateNetworkRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/networks")
internal class NetworkController(val networkService: NetworkService) {

  @GetMapping
  fun listNetworks(): Flux<Network> = // todo - Paging
    networkService.findNetworks()

  @GetMapping("/{networkId}")
  fun getNetwork(@PathVariable networkId: Long): Mono<Network> =
    networkService.findNetwork(networkId)

  @PostMapping
  fun createNetwork(@RequestBody request: CreateNetworkRequest): Mono<Network> =
    networkService.createNetwork(request.name, request.description)

  @PatchMapping("/{networkId}")
  fun updateNetwork(
    @PathVariable networkId: Long, @RequestBody request: UpdateNetworkRequest
  ): Mono<Network> {
    TODO()
  }
}
