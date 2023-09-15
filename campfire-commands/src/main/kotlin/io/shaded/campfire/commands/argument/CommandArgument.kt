package io.shaded.campfire.commands.argument

import io.shaded.campfire.commands.annotations.CampfireCommandDsl
import kotlin.reflect.KProperty

/**
 * Provides suggestions to the sender
 */
// TODO - Have a method to split the input properly like brigadier so we can
//  properly support /input <player> and then properly filter closer to the
//  actual suggestion rather than the ENTIRE player list.
typealias Suggestions<S> = (sender: S, input: String) -> Iterable<String>

class CommandArgument<S, T>(val name: String, val required: Boolean) {
  lateinit var description: String

  var suggestions: Suggestions<S>? = null

  @CampfireCommandDsl
  fun suggestions(suggestions: Suggestions<S>) {
    this.suggestions = suggestions
  }

  operator fun getValue(thisRef: Any?, property: KProperty<*>): T? = null
}



