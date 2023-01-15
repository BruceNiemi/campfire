package io.shaded.campfire.network

data class CreateNetworkRequest(
  val name: String,
  val description: String? = null
)
