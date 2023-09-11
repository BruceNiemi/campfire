package io.shaded.campfire.ui.menu

import io.shaded.campfire.ui.util.meta
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.meta.ItemMeta

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


fun menu(title: String, type: InventoryType, apply: Menu.() -> Unit): Menu {
  return Menu().apply(apply)
}

fun menu(
  title: String? = null,
  type: InventoryType = InventoryType.CHEST,
  size: MenuSize = SINGLE_CHEST,
  apply: Menu.() -> Unit,
): Menu {
  return Menu().apply(apply)
}

class Menu {
  private val items = arrayListOf<ItemComponent>()

  var updates = false
  var updateDelay = 1

  fun button(vararg slots: Int, builder: ItemComponent.() -> Unit) {
    this.items.add(ItemComponent(slots).apply(builder))
  }
}


