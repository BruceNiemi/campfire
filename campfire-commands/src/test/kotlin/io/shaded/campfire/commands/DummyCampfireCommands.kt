package io.shaded.campfire.commands

import io.shaded.campfire.commands.map.Platform

// Dummy implementation of the commands.
class DummyCampfireCommands : CampfireCommands<Unit>("dummy") {
  internal class DummyPlatform : Platform<Unit> {
    override fun registerCommand(command: CampfireCommand<Unit>) {
      // NOOP
    }

  }

  override fun register(command: CampfireCommand<Unit>): Boolean {
    this.commandMap.addCommand(command)
    return true
  }
}
