package pers.neige.mmtoni.hook.mythicmobs.impl

import io.lumine.utils.config.file.YamlConfiguration
import io.lumine.xikage.mythicmobs.MythicMobs
import io.lumine.xikage.mythicmobs.items.ItemManager
import io.lumine.xikage.mythicmobs.items.MythicItem
import javassist.ClassClassPath
import javassist.ClassPool
import javassist.CtClass
import pers.neige.mmtoni.hook.mythicmobs.MythicMobsHooker

/**
 * 4.5.9版本MM挂钩
 *
 * @constructor 启用4.5.9版本MM挂钩
 */
class MythicMobsHookerImpl459 : MythicMobsHooker() {
    private val test = YamlConfiguration()

    private val itemManager: ItemManager = MythicMobs.inst().itemManager

    override fun editItemManager() {
        val pool = ClassPool.getDefault()
        pool.appendClassPath(ClassClassPath(ItemManager::class.java))
        pool.appendClassPath(ClassClassPath(pers.neige.neigeitems.manager.ItemManager::class.java))

        try {
            val ctClass = pool.get("io.lumine.xikage.mythicmobs.items.ItemManager")

            val getItemStack = ctClass.getDeclaredMethod("getItemStack", arrayOf(pool.get("java.lang.String")))
            getItemStack.setBody("return pers.neige.neigeitems.manager.ItemManager.INSTANCE.getItemStack($1);")

            ctClass.writeFile(System.getProperty("user.dir") + "\\plugins\\MMToNI")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun editMythicItem() {
        val pool = ClassPool.getDefault()
        pool.appendClassPath(ClassClassPath(MythicItem::class.java))
        pool.appendClassPath(ClassClassPath(pers.neige.neigeitems.manager.ItemManager::class.java))

        try {
            val ctClass = pool.get("io.lumine.xikage.mythicmobs.items.MythicItem")

            var generateItemStack = ctClass.getDeclaredMethod("generateItemStack", arrayOf(CtClass.intType))
            generateItemStack.setBody("{org.bukkit.inventory.ItemStack itemStack = pers.neige.neigeitems.manager.ItemManager.INSTANCE.getItemStack(this.internalName);itemStack.setAmount($1);return new io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitItemStack(itemStack);}")

            generateItemStack = ctClass.getDeclaredMethod("generateItemStack", arrayOf(CtClass.intType, pool.get("io.lumine.xikage.mythicmobs.adapters.AbstractEntity"), pool.get("io.lumine.xikage.mythicmobs.adapters.AbstractEntity")))
            generateItemStack.setBody("{org.bukkit.inventory.ItemStack itemStack = pers.neige.neigeitems.manager.ItemManager.INSTANCE.getItemStack(this.internalName);itemStack.setAmount($1);return new io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitItemStack(itemStack);}")

            ctClass.writeFile(System.getProperty("user.dir") + "\\plugins\\MMToNI")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}