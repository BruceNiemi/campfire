package io.shaded.campfire.component.network.service

import io.shaded.campfire.network.Network
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface NetworkService {
  fun createNetwork(name: String, description: String? = null): Mono<Network>

  fun findNetworks(): Flux<Network>

  fun findNetwork(id: Long): Mono<Network>

  fun updateNetwork(id: Long, network: Network): Mono<Network>

  fun deleteNetwork(id: Long): Mono<Network>
}
