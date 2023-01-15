package io.shaded.campfire.component.network.repository

import io.shaded.campfire.component.network.model.NetworkDto
import io.shaded.campfire.network.Network
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface NetworkRepository : ReactiveCrudRepository<NetworkDto,Long > {
}
