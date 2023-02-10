package pers.neige.mmtoni.utils

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import pers.neige.neigeitems.manager.SectionManager
import taboolib.common.platform.function.getDataFolder
import taboolib.platform.BukkitPlugin
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


/**
 * 配置文件相关工具类
 */
object ConfigUtils {
    /**
     * 保存默认文件(不进行替换)
     */
    @JvmStatic
    fun BukkitPlugin.saveResourceNotWarn(resourcePath: String) {
        this.getResource(resourcePath.replace('\\', '/'))?.let { inputStream ->
            val outFile = File(this.dataFolder, resourcePath)
            val lastIndex: Int = resourcePath.lastIndexOf(File.separator)
            val outDir = File(this.dataFolder, resourcePath.substring(0, if (lastIndex >= 0) lastIndex else 0))
            if (!outDir.exists()) {
                outDir.mkdirs()
            }
            if (!outFile.exists()) {
                try {
                    var len: Int
                    val fileOutputStream = FileOutputStream(outFile)
                    val buf = ByteArray(1024)
                    while (inputStream.read(buf).also { len = it } > 0) {
                        (fileOutputStream as OutputStream).write(buf, 0, len)
                    }
                    fileOutputStream.close()
                    inputStream.close()
                } catch (ex: IOException) {}
            }
        }
    }
}