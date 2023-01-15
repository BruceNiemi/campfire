package io.shaded.campfire.component.network.service

import io.shaded.campfire.network.Network
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
internal class NetworkServiceImpl : NetworkService {
  override fun createNetwork(
    name: String,
    description: String?
  ): Mono<Network> {
    TODO("Not yet implemented")
  }

  override fun findNetworks(): Flux<Network> {
    TODO("Not yet implemented")
  }

  override fun findNetwork(id: Long): Mono<Network> {
    TODO("Not yet implemented")
  }

  override fun updateNetwork(id: Long, network: Network): Mono<Network> {
    TODO("Not yet implemented")
  }

  override fun deleteNetwork(id: Long): Mono<Network> {
    TODO("Not yet implemented")
  }

}
