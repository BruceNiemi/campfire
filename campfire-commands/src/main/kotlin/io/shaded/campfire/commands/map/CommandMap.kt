package io.shaded.campfire.commands.map

import io.shaded.campfire.commands.CampfireCommand

class CommandMap<S>(val prefix: String, val platform: Platform<S>) {
  private val children = mutableMapOf<String, CampfireCommand<S>>()

  fun addCommand(command: CampfireCommand<S>) {
    check(!children.containsKey(command.name)) { "Child with name ${command.name} already exists." }
    this.children[command.name] = command

    check(!children.containsKey("$prefix:${command.name}")) {
      "Child with name $prefix:${command.name} already exists."
    }
    this.children["$prefix:${command.name}"] = command

    command.aliases.forEach {
      check(!children.containsKey(it)) { "Child with alias $it already exists." }
      this.children[it] = command
    }

    this.platform.registerCommand(command)
  }

  fun getCommand(name: String): CampfireCommand<S>? = this.children[name]
}

