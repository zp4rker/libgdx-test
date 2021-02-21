package com.zp4rker.libgdxtest

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author zp4rker
 */
class TestGame : ApplicationAdapter() {

    private lateinit var batch: SpriteBatch

    private var ticks = 0L

    private lateinit var car: Sprite
    private var carX = 0f
    private var carY = 0f
    private var acc = 1f

    private lateinit var steeringWheel: Sprite

    override fun create() {
        batch = SpriteBatch()

        car = Sprite(Texture("car.jpg"))
        car.setSize(car.width / 3f, car.height / 3f)
        car.setOrigin(car.width / 2f, car.height / 2f)

        steeringWheel = Sprite(Texture("steering_wheel.png"))
        steeringWheel.setSize(100f, 100f)
        steeringWheel.setPosition(800 - 100 - 5f, 600 - 200 - 5f)
        steeringWheel.setOrigin(50f, 50f)
    }

    override fun render() {
        ticks++

        if (ticks % 5 == 0L) {
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
                acc += 0.1f
            } else {
                acc = (acc - 0.1f).let { if (it > 1) it else 1f }
            }
        }

        when {
            Gdx.input.isKeyPressed(Input.Keys.RIGHT) -> {
                steeringWheel.rotation = (steeringWheel.rotation - 3f * acc).let { if (it > -450) it else -450f }
            }

            Gdx.input.isKeyPressed(Input.Keys.LEFT) -> {
                steeringWheel.rotation = (steeringWheel.rotation + 3f * acc).let { if (it < 450) it else 450f }
            }
        }

        when {
            Gdx.input.isKeyPressed(Input.Keys.UP) -> {
                carX += -1 * sin(rad(car.rotation)) * acc
                carY += cos(rad(car.rotation)) * acc

                car.rotation += (steeringWheel.rotation / 25f) * 0.05f
                if (!(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
                    steeringWheel.rotation += if (steeringWheel.rotation > 0) -1 * acc else 1f * acc
                }
            }

            Gdx.input.isKeyPressed(Input.Keys.DOWN) -> {
                carX -= -1 * sin(rad(car.rotation)) * acc
                carY -= cos(rad(car.rotation)) * acc

                car.rotation -= (steeringWheel.rotation / 20f) * 0.05f
            }

            else -> acc = 1f
        }

        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.begin()
        car.setPosition(carX, carY)
        car.draw(batch)
        steeringWheel.draw(batch)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        car.texture.dispose()
    }

    private fun rad(degrees: Float): Float = Math.toRadians(degrees.toDouble()).toFloat()
}