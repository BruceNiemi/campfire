package io.shaded.campfire.commands.map

import io.shaded.campfire.commands.CampfireCommand

class CommandMap<S>(private val prefix: String) {
  private val children = mutableMapOf<String, CampfireCommand<S>>()

  fun addCommand(command: CampfireCommand<S>) {
    check(!children.containsKey(command.name)) { "Command with name '${command.name}' already exists." }
    this.children[command.name] = command

    check(!children.containsKey("$prefix:${command.name}")) {
      "Command with name '$prefix:${command.name}' already exists."
    }
    this.children["$prefix:${command.name}"] = command

    command.aliases.forEach {
      check(!children.containsKey(it)) { "Command with alias '$it' already exists." }
      this.children[it] = command
    }
  }

  fun getCommand(name: String): CampfireCommand<S>? = this.children[name]

  fun getCommandLabels(): List<String> =
    this.children.keys.toList() // Bruce - Does this need to be a list?
}

