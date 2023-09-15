package io.shaded.campfire.commands.argument

import io.shaded.campfire.commands.annotations.CampfireCommandDsl
import kotlin.reflect.KProperty

typealias Suggestions<S> = (sender: S, input: List<String>) -> Iterable<String>

class CommandArgument<S, T>(val name: String, val required: Boolean) {
  lateinit var description: String

  var suggestions: Suggestions<S>? = null

  @CampfireCommandDsl
  fun suggestions(suggestions: Suggestions<S>) {
    this.suggestions = suggestions
  }

  operator fun getValue(thisRef: Any?, property: KProperty<*>): T? = null
}



