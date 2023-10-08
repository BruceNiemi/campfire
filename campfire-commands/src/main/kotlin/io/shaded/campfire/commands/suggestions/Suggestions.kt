package io.shaded.campfire.commands.suggestions

typealias Suggestions<S> = (sender: S, input: List<String>) -> Iterable<String>

val EMPTY: Suggestions<*> = { _, _ -> emptyList() }

fun <S> suggestions(vararg suggestions: String): Suggestions<S> =
  { _, _ ->
    suggestions.toList()
  }
