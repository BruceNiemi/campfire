package io.shaded.campfire.network

data class UpdateNetworkRequest(
  val name: String,
  val description: String? = null
)
