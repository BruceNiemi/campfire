package io.shaded.campfire.commands.arguments

import java.util.*
import kotlin.reflect.KProperty

abstract class Argument<S, T : Any?>(val name: String) {
  abstract var parsed: T

  operator fun getValue(thisRef: Any?, property: KProperty<*>): T =
    this.parsed

  abstract fun parse(sender: S, input: Queue<String>)

  open fun suggest(sender: S, input: List<String>): Iterable<String> =
    emptyList()
}
