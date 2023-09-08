package io.shaded.campfire.common.item

import io.shaded.campfire.common.annotation.CampfireDSL
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

/**
 *
 */
@CampfireDSL
fun itemStack(material: Material, builder: ItemStack.() -> Unit) =
  ItemStack(material).apply(builder)

/**
 *
 */
@CampfireDSL
fun itemStack(itemStack: ItemStack, builder: ItemStack.() -> Unit) =
  itemStack.clone().apply(builder)


@CampfireDSL
@Suppress("UNCHECKED_CAST")
fun <M : ItemMeta> ItemStack.meta(builder: M.() -> Unit) {
  itemMeta = (itemMeta as? M)?.apply(builder) ?: itemMeta
}

@CampfireDSL
fun ItemStack.flag(flag: ItemFlag) = addItemFlags(flag)

@CampfireDSL
fun ItemStack.flags(vararg flags: ItemFlag) = addItemFlags(*flags)

@CampfireDSL
fun ItemStack.removeFlag(flag: ItemFlag) = removeItemFlags(flag)

@CampfireDSL
fun ItemStack.removeFlags(vararg flags: ItemFlag) = removeItemFlags(*flags)
