package io.shaded.campfire.commands.arguments

import io.shaded.campfire.commands.CampfireCommand
import io.shaded.campfire.commands.arguments.parser.IntegerArgument
import io.shaded.campfire.commands.suggestions.Suggestions

fun <S, T : Any> RequiredArgument<S, T>.optional(): OptionalAdapter<S, T> =
  OptionalAdapter(this)

fun <S, T : Any> RequiredArgument<S, T>.default(
  default: T
): DefaultArgumentAdapter<S, T> =
  DefaultArgumentAdapter({ default }, this)

fun <S, T : Any> RequiredArgument<S, T>.default(
  supplier: DefaultArgumentSupplier<S, T>
): DefaultArgumentAdapter<S, T> =
  DefaultArgumentAdapter(supplier, this)

fun <S> CampfireCommand<S>.integer(
  name: String,
  min: Int = Int.MIN_VALUE,
  max: Int = Int.MAX_VALUE
): RequiredArgument<S, Int> = IntegerArgument(name, min, max)
