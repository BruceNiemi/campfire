package io.shaded.campfire.commands.assertions

class AssertionSet<S> {
  private val assertions: HashSet<Assertion<S>> = hashSetOf()

  fun evaluate(sender: S): String? {
    return assertions
      .firstOrNull { !it.test(sender) }
      ?.failed(sender)
  }

  /**
   * Adds the given [precondition] to the [AssertionSet] using the unary plus operator.
   */
  operator fun Assertion<S>.unaryPlus() {
    assertions.add(this)
  }
}
