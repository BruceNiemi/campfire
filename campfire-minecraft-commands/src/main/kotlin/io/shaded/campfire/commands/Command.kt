package io.shaded.campfire.commands

import io.shaded.campfire.commands.argument.CommandArgument
import io.shaded.campfire.commands.assertions.AssertionSet

/**
 * Represents a command with a label and an action to execute.
 * @property label Name of the command.
 * @property body The action to execute.
 * @param <S> The type of the command sender.
 */
class Command<S>(val label: String, val aliases: MutableList<String>) {
  val arguments: ArrayList<CommandArgument<*>> = arrayListOf()
  lateinit var preconditions: AssertionSet<S>

  val hasPreconditions: Boolean
    get() = ::preconditions.isInitialized

  lateinit var body: S.() -> Unit

  val hasBody: Boolean
    get() = ::body.isInitialized

  var subCommands: CommandTree? = null

  /**
   * Sets the action to execute for this command.
   * @param action The action to execute.
   */
  @CommandDSL
  fun action(action: S.() -> Unit) {
    this.body = action
  }

  override fun toString(): String {
    return "Command(label='$label', arguments=$arguments, subCommands=$subCommands)"
  }

}

@CommandDSL
fun <S> Command<S>.command(vararg labels: String, init: Command<S>.() -> Unit) {
  if (this.subCommands == null) {
    this.subCommands = CommandTree()
  }
  val aliases = labels.toMutableList()

  if (aliases.isNotEmpty()) {
    aliases.removeFirst()
  }

  this.subCommands!!.insert(labels.toList(), Command<S>(labels[0], aliases).apply(init))
}

@CommandDSL
fun <S> Command<S>.assertions(init: AssertionSet<S>.() -> Unit): AssertionSet<S> {
  val preconditionSet = AssertionSet<S>()
  preconditionSet.init()
  preconditions = preconditionSet
  return preconditionSet
}
