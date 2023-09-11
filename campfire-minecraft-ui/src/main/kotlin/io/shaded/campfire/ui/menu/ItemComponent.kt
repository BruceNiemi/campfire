package io.shaded.campfire.ui.menu

import io.shaded.campfire.ui.util.itemStack
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class ItemComponent(val slots: IntArray) : Component() {
  lateinit var body: InventoryClickEvent.() -> Unit
  lateinit var item: ItemStack

  fun item(material: Material, builder: ItemStack.() -> Unit) {
    this.item = itemStack(material, builder)
  }

  fun action(action: InventoryClickEvent.() -> Unit) {
    body = action
  }
}
