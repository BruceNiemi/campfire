package io.shaded.campfire.network

/**
 * A network that is tied to the campfire systems.
 *
 * @property id The unique id of the network
 * @property name The name of the network 'e.g Minehub, Hypixel, etc..'
 * @property description The message that is displayed to the player when trying to
 * connect to the server.
 * @property domains The domains that the network owns.
 */
data class Network(
  val id: Long,
  val name: String,
  val description: String? = null,
  val domains: List<Domain>
)

data class Domain(val id: Long, val domain: String)
