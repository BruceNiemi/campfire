package io.shaded.campfire.ui.util

import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

/**
 *
 */

fun itemStack(material: Material, builder: ItemStack.() -> Unit) =
  ItemStack(material).apply(builder)

/**
 *
 */

fun itemStack(itemStack: ItemStack, builder: ItemStack.() -> Unit) =
  itemStack.clone().apply(builder)



@Suppress("UNCHECKED_CAST")
fun <M : ItemMeta> ItemStack.meta(builder: M.() -> Unit) {
  itemMeta = (itemMeta as? M)?.apply(builder) ?: itemMeta
}


fun ItemStack.flag(flag: ItemFlag) = addItemFlags(flag)


fun ItemStack.flags(vararg flags: ItemFlag) = addItemFlags(*flags)


fun ItemStack.removeFlag(flag: ItemFlag) = removeItemFlags(flag)


fun ItemStack.removeFlags(vararg flags: ItemFlag) = removeItemFlags(*flags)
