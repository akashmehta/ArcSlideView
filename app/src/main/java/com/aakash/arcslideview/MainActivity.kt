package com.aakash.arcslideview

import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Display
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.PI

class MainActivity : AppCompatActivity() {

    private var centerX: Float? = null
    private var centerY: Float? = null

    private var radius: Double = 0.0

    private var maxScreenWidth: Int = 0

    private var imageArray: Array<ImageView>? = null
    private var imageBgArray: Array<ImageView>? = null

    private var currentIndex: Int = 0

    private val imageLimit : Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val display: Display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        maxScreenWidth = size.x

        centerX = (maxScreenWidth / 2).toFloat()
        centerY = -450f

        radius = (maxScreenWidth / 2).plus(350).toDouble()

        imageArray = Array(imageLimit) {
            ImageView(this)
        }
        imageBgArray = Array(imageLimit) {
            ImageView(this)
        }

        for (i in 0 until imageLimit) {
            imageArray!![i] = ImageView(this)
            when (i % 3) {
                0 -> imageArray!![i].setImageResource(R.drawable.sample_icon_1)
                1 -> imageArray!![i].setImageResource(R.drawable.sample_icon_2)
                2 -> imageArray!![i].setImageResource(R.drawable.sample_icon_3)
            }
            imageBgArray!![i].setImageResource(R.drawable.sample_background)
            imageArray!![i].visibility = View.GONE

            imageBgArray!![i].visibility = View.GONE
            rlParent.addView(imageBgArray!![i])
            rlParent.addView(imageArray!![i])

            imageArray!![i].layoutParams.height = 400
            imageArray!![i].layoutParams.width = 400
        }

        rlParent.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeRight() {

                if (currentIndex == imageLimit - 1) {
                    return
                }
                println("MainActivity.onSwipeRight")
                imageArray!![currentIndex + 1] reverseSwipe Triple(180.0, 90.0, 100)
                imageArray!![currentIndex] reverseSwipe Triple(90.0, 0.0, 100)

                imageBgArray!![currentIndex + 1] reverseSwipe Triple(180.0, 90.0, 1)
                imageBgArray!![currentIndex] reverseSwipe Triple(90.0, 0.0, 201)

                currentIndex++
            }

            override fun onSwipeLeft() {
                if (currentIndex == 0) {
                    return
                }
                println("MainActivity.onSwipeLeft")
                imageArray!![currentIndex] swipe Triple(90.0, 180.0, 100)
                imageArray!![currentIndex - 1] swipe Triple(0.0, 90.0, 100)

                imageBgArray!![currentIndex] swipe Triple(90.0, 180.0, 201)
                imageBgArray!![currentIndex - 1] swipe Triple(0.0, 90.0, 1)
                currentIndex--
            }
        })

    }

    private infix fun View.swipe(angleParam: Triple<Double, Double, Long>) {
        var degree = angleParam.first
        val handler = Handler()
        var runnable: Runnable? = null
        runnable = Runnable {
            if (degree <= angleParam.second) {
                this.x = (centerX!! + (radius * Math.cos(degree * PI / 180))).toFloat() - this.width / 2
                this.y = (centerY!! + (radius * Math.sin(degree * PI / 180))).toFloat() - this.height / 2
                this.rotation = (degree + 270).toFloat()
                degree += 5
                handler.postDelayed(runnable, 30)
            }
        }
        handler.postDelayed(runnable, angleParam.third)
    }

    private infix fun View.reverseSwipe(angleParam: Triple<Double, Double, Long>) {
        if (this.visibility == View.GONE) {
            this.visibility = View.VISIBLE
        }
        var degree = angleParam.first
        val handler = Handler()
        var runnable: Runnable? = null
        runnable = Runnable {
            if (degree >= angleParam.second) {
                this.x = (centerX!! + (radius * Math.cos(degree * PI / 180))).toFloat() - this.width / 2
                this.y = (centerY!! + (radius * Math.sin(degree * PI / 180))).toFloat() - this.height / 2
                this.rotation = (degree + 270).toFloat()
                degree -= 5
                handler.postDelayed(runnable, 30)
            }
        }
        handler.postDelayed(runnable, angleParam.third)
    }
}
