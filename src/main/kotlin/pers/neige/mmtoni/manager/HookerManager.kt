package pers.neige.mmtoni.manager

import pers.neige.mmtoni.hook.mythicmobs.MythicMobsHooker
import pers.neige.mmtoni.hook.mythicmobs.impl.MythicMobsHookerImpl459
import pers.neige.mmtoni.hook.mythicmobs.impl.MythicMobsHookerImpl490
import pers.neige.mmtoni.hook.mythicmobs.impl.MythicMobsHookerImpl502
import pers.neige.mmtoni.hook.mythicmobs.impl.MythicMobsHookerImpl510
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake

object HookerManager {
    // 某些情况下 MythicMobs 的 ItemManager 加载顺序很奇怪，因此写成 by lazy, 然后在 active 阶段主动调用
    // 没事儿改包名很爽吗, 写MM的, 你妈死了
    val mythicMobsHooker: MythicMobsHooker? by lazy {
        try {
            // 5.0.3+
            MythicMobsHookerImpl510()
        } catch (error: Throwable) {
            try {
                // 5.0.3-
                MythicMobsHookerImpl502()
            } catch (error: Throwable) {
                try {
                    // 5.0.0-
                    MythicMobsHookerImpl490()
                } catch (error: Throwable) {
                    // 4.7.2-
                    MythicMobsHookerImpl459()
                }
            }
        }
    }

    /**
     * 加载MM挂钩功能
     */
    @Awake(LifeCycle.ACTIVE)
    fun loadMythicMobsHooker() {
        mythicMobsHooker?.editItemManager()
        mythicMobsHooker?.editMythicItem()
    }
}