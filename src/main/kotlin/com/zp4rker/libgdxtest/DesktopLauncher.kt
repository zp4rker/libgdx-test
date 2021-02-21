package com.zp4rker.libgdxtest

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration

/**
 * @author zp4rker
 */
class DesktopLauncher {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val config = LwjglApplicationConfiguration()

            config.width = 800
            config.height = 500

            LwjglApplication(TestGame(), config)
        }
    }
}