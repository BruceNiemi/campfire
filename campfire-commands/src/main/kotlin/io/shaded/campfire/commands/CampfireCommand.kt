package io.shaded.campfire.commands

import io.shaded.campfire.commands.annotations.CampfireCommandDsl
import io.shaded.campfire.commands.arguments.Argument
import io.shaded.campfire.commands.arguments.DefaultArgument
import io.shaded.campfire.commands.arguments.OptionalArgument
import io.shaded.campfire.commands.arguments.RequiredArgument
import io.shaded.campfire.commands.arguments.parser.StringArgument

/**
 * Represents an action associated with a command, which is executed only
 * when the preconditions for the command are met.
 *
 * @param S The type of the sender for the command.
 */
typealias CommandAction<S> = S.() -> Unit

/**
 * Represents a function that determines whether a sender can execute a
 * specific command.
 *
 * @param S The type of the sender for the command.
 * @return `true` if the sender is allowed to execute the command; `false` otherwise.
 */
typealias CommandCondition<S> = (sender: S, input: List<String>) -> Boolean

class CampfireCommand<S>(val name: String, val aliases: Array<out String>) {
  val conditions = hashSetOf<CommandCondition<S>>()
  val children = mutableMapOf<String, CampfireCommand<S>>()
  val arguments = mutableListOf<Argument<S, *>>()
  lateinit var body: CommandAction<S>

  val hasBody: Boolean
    get() = ::body.isInitialized

  /**
   * Sets the action to be executed when this command is invoked.
   *
   * @param action The action to be executed when the command is invoked.
   */
  @CampfireCommandDsl
  fun execute(action: CommandAction<S>) {
    check(!this::body.isInitialized) {
      "The command executor can only be set once."
    }

    body = action
  }

  /**
   * Sets a condition to be checked before this command is invoked.
   *
   * @param condition The condition to be checked when the command is invoked.
   */
  @CampfireCommandDsl
  fun condition(condition: CommandCondition<S>) {
    this.conditions.add(condition)
  }

  /**
   * A DSL function to create and configure a [CampfireCommand] instance.
   *
   * This function allows for a concise way to create and configure a [CampfireCommand].
   *
   * @param name The name of the command.
   * @param aliases The command aliases.
   * @param init A lambda expression that configures the [CampfireCommand].
   * @return A configured [CampfireCommand] instance.
   */
  @CampfireCommandDsl
  fun command(
    name: String,
    vararg aliases: String,
    init: CampfireCommand<S>.() -> Unit
  ) {
    val command = CampfireCommand<S>(name, aliases).apply(init)

    check(!children.containsKey(command.name)) { "Child with name ${command.name} already exists." }
    this.children[command.name] = command

    command.aliases.forEach {
      check(!children.containsKey(it)) { "Child with alias $it already exists." }
      this.children[it] = command
    }
  }

  fun validate() {
    // A root command if there is children can only execute iff there are no
    // arguments.
    check(!(hasBody && children.isNotEmpty() && arguments.isEmpty())) {
      "A root command can only have a body if there is no arguments attached."
    }

    var requiredArgumentFound = false
    var defaultArgumentFound = false
    var optionalArgumentFound = false
    var greedyStringFound = false

    for (argument in arguments) {
      when (argument) {
        is RequiredArgument<*, *> -> {
          if (optionalArgumentFound || greedyStringFound) {
            throw IllegalArgumentException("Required argument '${argument.name}' cannot appear after optional or greedy string arguments.")
          }
          requiredArgumentFound = true
        }
        is OptionalArgument<*, *> -> {
          if (greedyStringFound) {
            throw IllegalArgumentException("Optional argument '${argument.name}' cannot appear after a greedy string argument.")
          }
          optionalArgumentFound = true
        }
        is DefaultArgument<*, *> -> {
          if (optionalArgumentFound || greedyStringFound) {
            throw IllegalArgumentException("Default argument '${argument.name}' cannot appear after optional or greedy string arguments.")
          }
          defaultArgumentFound = true
        }
        else -> {
          throw IllegalArgumentException("Unknown argument type for argument '${argument.name}'.")
        }
      }

      if (argument is StringArgument<S> && argument.type == StringArgument.StringType.GREEDY_PHRASE) {
        if (optionalArgumentFound || defaultArgumentFound || requiredArgumentFound) {
          throw IllegalArgumentException("Greedy string argument '${argument.name}' cannot appear with other types of arguments.")
        }
        greedyStringFound = true
      }
    }


  }

}
