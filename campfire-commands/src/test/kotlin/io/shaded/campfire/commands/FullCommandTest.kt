package io.shaded.campfire.commands

import io.shaded.campfire.commands.map.Platform
import org.junit.jupiter.api.Test

internal class FullCommandTest {

  class NoOpPlatform : Platform<Unit> {
    override fun registerCommand(command: CampfireCommand<Unit>) {

    }
  }

  class NoOpCampfireCommands : CampfireCommands<Unit>("unit", NoOpPlatform()) {
    override fun register(command: CampfireCommand<Unit>): Boolean {
      this.commandMap.addCommand(command)
      return true
    }
  }

  val campfireCommands = NoOpCampfireCommands()

  @Test
  fun `test insert commands`() {

  }
}
