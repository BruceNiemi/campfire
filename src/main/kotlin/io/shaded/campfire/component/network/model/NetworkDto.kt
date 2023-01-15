package io.shaded.campfire.component.network.model

import org.springframework.data.annotation.Id

data class NetworkDto(
  @Id val id: Long, val name: String, val description: String? = null
)
