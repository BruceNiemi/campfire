package io.shaded.campfire.ui.menu

import org.bukkit.event.inventory.InventoryType

fun main() {

}

val SINGLE_CHEST = MenuSize(9 * 3)
val DOUBLE_CHEST = MenuSize(9 * 6)

@JvmInline
value class MenuSize(val size: Int) {
  init {
    require(size % 9 == 0) {
      "Menu size must be dividable by nine"
    }
  }
}

/**
 * The goal is to have react like components that can be reused in multiple
 * menus without much hassle. This might include some sort of react like
 * state management system to handle dynamic refreshes of data thus being in
 * a HUD, Scoreboard, Action bar etc...
 */
abstract class Component {

}

fun menu(title: String, type: InventoryType, apply: Menu.() -> Unit): Menu {
  return Menu().apply(apply)
}


fun menu(
  title: String,
  type: InventoryType = InventoryType.CHEST,
  size: MenuSize = SINGLE_CHEST,
  apply: Menu.() -> Unit,
): Menu {
  return Menu().apply(apply)
}

class Menu {}
