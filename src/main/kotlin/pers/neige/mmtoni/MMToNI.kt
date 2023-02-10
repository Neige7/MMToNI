package pers.neige.mmtoni

import org.bukkit.Bukkit
import taboolib.common.platform.Plugin
import taboolib.platform.BukkitPlugin

object MMToNI : Plugin() {
    val plugin by lazy { BukkitPlugin.getInstance() }

    val bukkitScheduler by lazy { Bukkit.getScheduler() }
}