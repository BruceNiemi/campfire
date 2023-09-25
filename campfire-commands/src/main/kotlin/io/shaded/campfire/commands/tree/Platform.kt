package io.shaded.campfire.commands.tree

import io.shaded.campfire.commands.CampfireCommand

interface Platform<S> {
  fun registerCommand(command: CampfireCommand<S>)
}
