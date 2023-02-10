package pers.neige.mmtoni.manager

import pers.neige.mmtoni.MMToNI.plugin
import pers.neige.mmtoni.utils.ConfigUtils.saveResourceNotWarn
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.Platform
import taboolib.module.metrics.Metrics

// 配置文件管理器, 用于管理config.yml文件, 对其中缺少的配置项进行主动补全, 同时释放默认配置文件
object ConfigManager {
    /**
     * 加载默认配置文件
     */
    @Awake(LifeCycle.INIT)
    fun saveResource() {
        plugin.saveResourceNotWarn("使用指南.txt")
        // 加载bstats
        val metrics = Metrics(17547, plugin.description.version, Platform.BUKKIT)
    }
}
