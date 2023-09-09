package io.shaded.campfire.ui.state

/**
 * Used to hold state in an interface by delegating the state into the opener
 * of the menu to always have the most up-to-date values when rendering the
 * menu to the player.
 */
class State {
  private val states = hashMapOf<String, () -> Any>()

  fun <T> get(key: StateKey<T>): T? {
    val supplier = this.states[key.name]

    check(supplier == null) {
      "The value at $key cannot be null"
    }

    @Suppress("UNCHECKED_CAST")
    return supplier?.invoke() as T
  }

  fun <T> getOrDefault(key: StateKey<T>, default: T): T {
    val supplier = this.states[key.name]

    if (supplier == null) {
      return default
    }

    @Suppress("UNCHECKED_CAST")
    return supplier.invoke() as T
  }
}
