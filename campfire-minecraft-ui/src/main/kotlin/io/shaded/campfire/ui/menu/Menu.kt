package io.shaded.campfire.ui.menu

import io.shaded.campfire.ui.util.meta
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

fun main() {

}

val a_ = menu {

}

val menu = menu("this is it") {
  updates = true
  updateDelay = 5

  button(0, 1) {
    item(Material.BOOK) {
      meta<ItemMeta> {
        displayName(net.kyori.adventure.text.Component.empty())
      }
    }

    action {
      // ...
    }
  }

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

class ItemComponent : Component() {
  lateinit var body: InventoryClickEvent.() -> Unit
  fun item(material: Material, builder: ItemStack.() -> Unit) {}

  fun action(action: InventoryClickEvent.() -> Unit) {
    body = action
  }

}

fun menu(title: String, type: InventoryType, apply: Menu.() -> Unit): Menu {
  return Menu().apply(apply)
}


fun menu(
  title: String = "",
  type: InventoryType = InventoryType.CHEST,
  size: MenuSize = SINGLE_CHEST,
  apply: Menu.() -> Unit,
): Menu {
  return Menu().apply(apply)
}

class Menu(val inventory: Inventory? = null) {
  var updates = false
  var updateDelay = 1
}


fun Menu.button(vararg slots: Int, builder: ItemComponent.() -> Unit) {}
