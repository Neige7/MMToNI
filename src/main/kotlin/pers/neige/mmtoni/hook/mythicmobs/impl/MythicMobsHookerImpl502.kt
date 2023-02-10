package pers.neige.mmtoni.hook.mythicmobs.impl

import io.lumine.mythic.bukkit.MythicBukkit
import io.lumine.mythic.core.items.ItemExecutor
import io.lumine.mythic.core.items.MythicItem
import io.lumine.mythic.utils.config.file.YamlConfiguration
import javassist.ClassClassPath
import javassist.ClassPool
import javassist.CtClass
import pers.neige.mmtoni.hook.mythicmobs.MythicMobsHooker
import pers.neige.neigeitems.manager.ItemManager

/**
 * 5.0.2版本MM挂钩
 *
 * @constructor 启用5.0.2版本MM挂钩
 */
class MythicMobsHookerImpl502 : MythicMobsHooker() {
    private val test = YamlConfiguration()

    private val itemManager: ItemExecutor = MythicBukkit.inst().itemManager

    override fun editItemManager() {
        val pool = ClassPool.getDefault()
        pool.appendClassPath(ClassClassPath(ItemExecutor::class.java))
        pool.appendClassPath(ClassClassPath(ItemManager::class.java))

        try {
            val ctClass = pool.get("io.lumine.mythic.core.items.ItemExecutor")

            var getItemStack = ctClass.getDeclaredMethod("getItemStack", arrayOf(pool.get("java.lang.String")))
            getItemStack.setBody("return pers.neige.neigeitems.manager.ItemManager.INSTANCE.getItemStack($1);")

            getItemStack = ctClass.getDeclaredMethod("getItemStack", arrayOf(pool.get("java.lang.String"), CtClass.intType))
            getItemStack.setBody("{org.bukkit.inventory.ItemStack itemStack = pers.neige.neigeitems.manager.ItemManager.INSTANCE.getItemStack($1);itemStack.setAmount($2);return itemStack;}")

            ctClass.writeFile(System.getProperty("user.dir") + "\\plugins\\MMToNI")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun editMythicItem() {
        val pool = ClassPool.getDefault()
        pool.appendClassPath(ClassClassPath(MythicItem::class.java))
        pool.appendClassPath(ClassClassPath(ItemManager::class.java))

        try {
            val ctClass = pool.get("io.lumine.mythic.core.items.MythicItem")

            var generateItemStack = ctClass.getDeclaredMethod("generateItemStack", arrayOf(CtClass.intType))
            generateItemStack.setBody("{org.bukkit.inventory.ItemStack itemStack = pers.neige.neigeitems.manager.ItemManager.INSTANCE.getItemStack(this.internalName);itemStack.setAmount($1);return new io.lumine.mythic.bukkit.adapters.BukkitItemStack(itemStack);}")

            generateItemStack = ctClass.getDeclaredMethod("generateItemStack", arrayOf(CtClass.intType, pool.get("io.lumine.mythic.api.adapters.AbstractEntity"), pool.get("io.lumine.mythic.api.adapters.AbstractEntity")))
            generateItemStack.setBody("{org.bukkit.inventory.ItemStack itemStack = pers.neige.neigeitems.manager.ItemManager.INSTANCE.getItemStack(this.internalName);itemStack.setAmount($1);return new io.lumine.mythic.bukkit.adapters.BukkitItemStack(itemStack);}")

            generateItemStack = ctClass.getDeclaredMethod("generateItemStack", arrayOf(pool.get("io.lumine.mythic.api.drops.DropMetadata"), CtClass.intType))
            generateItemStack.setBody("{org.bukkit.inventory.ItemStack itemStack = pers.neige.neigeitems.manager.ItemManager.INSTANCE.getItemStack(this.internalName);itemStack.setAmount($2);return new io.lumine.mythic.bukkit.adapters.BukkitItemStack(itemStack);}")

            ctClass.writeFile(System.getProperty("user.dir") + "\\plugins\\MMToNI")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}