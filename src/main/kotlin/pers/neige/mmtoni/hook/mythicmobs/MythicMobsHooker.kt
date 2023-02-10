package pers.neige.mmtoni.hook.mythicmobs

/**
 * MM挂钩
 */
abstract class MythicMobsHooker {
    /**
     * 编辑MythicMobs物品管理器
     */
    abstract fun editItemManager()

    /**
     * 编辑MythicMobs物品类
     */
    abstract fun editMythicItem()
}