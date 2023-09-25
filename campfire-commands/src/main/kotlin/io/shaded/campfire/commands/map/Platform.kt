package io.shaded.campfire.commands.map

import io.shaded.campfire.commands.CampfireCommand

interface Platform<S> {
  fun registerCommand(command: CampfireCommand<S>)
}
