package io.shaded.campfire.ui

import io.shaded.campfire.ui.annotation.CampfireUiDsl
import io.shaded.campfire.ui.configuration.CampfireUiConfiguration
import org.bukkit.plugin.java.JavaPlugin
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@CampfireUiDsl
@OptIn(ExperimentalContracts::class)
public fun campfireUi(
  plugin: JavaPlugin,
  builder: CampfireUiConfiguration. () -> Unit
): CampfireUi {
  contract {
    callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
  }

  return CampfireUi(plugin, CampfireUiConfiguration().apply(builder))
}

public class CampfireUi internal constructor(
  public val plugin: JavaPlugin,
  public val configuration: CampfireUiConfiguration
)
