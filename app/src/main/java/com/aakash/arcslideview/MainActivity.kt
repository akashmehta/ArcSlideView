package com.aakash.arcslideview

import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Display
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.PI

class MainActivity : AppCompatActivity() {

    private var centerX: Float? = null
    private var centerY: Float? = null

    private var radius: Double = 0.0

    private var maxScreenWidth: Int = 0

    private var imageArray: Array<View>? = null
    private var imageBgArray: Array<View>? = null

    private var currentIndex: Int = 0

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

        imageArray = Array(3) {
            ivCenterImage
        }
        imageArray!![0] = ivStartImage
        imageArray!![1] = ivCenterImage
        imageArray!![2] = ivEndImage

        imageBgArray = Array(3) {
            ivBg1
        }
        imageBgArray!![0] = ivBg1
        imageBgArray!![1] = ivBg2
        imageBgArray!![2] = ivBg3

        rlParent.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeRight() {

                if (currentIndex == 2) {
                    return
                }
                println("MainActivity.onSwipeRight")
                imageArray!![currentIndex + 1] reverseSwipe Pair(180.0, 90.0)
                imageArray!![currentIndex] reverseSwipe Pair(90.0, 0.0)

                imageBgArray!![currentIndex + 1] bgReverseSwipe1 Pair(180.0, 90.0)
                imageBgArray!![currentIndex] bgReverseSwipe2 Pair(90.0, 0.0)

                currentIndex++
            }

            override fun onSwipeLeft() {
                if (currentIndex == 0) {
                    return
                }
                println("MainActivity.onSwipeLeft")
                imageArray!![currentIndex] swipe Pair(90.0, 180.0)
                imageArray!![currentIndex - 1] swipe Pair(0.0, 90.0)

                imageBgArray!![currentIndex] bgSwipe1 Pair(90.0, 180.0)
                imageBgArray!![currentIndex - 1] bgSwipe2 Pair(0.0, 90.0)
                currentIndex--
            }
        })

    }

    private infix fun View.swipe(anglePair: Pair<Double, Double>) {
        var degree = anglePair.first
        val handler = Handler()
        var runnable: Runnable? = null
        runnable = Runnable {
            if (degree <= anglePair.second) {
                this.x = (centerX!! + (radius * Math.cos(degree * PI / 180))).toFloat() - this.width / 2
                this.y = (centerY!! + (radius * Math.sin(degree * PI / 180))).toFloat() - this.height / 2
                this.rotation = (degree + 270).toFloat()
                degree += 5
                handler.postDelayed(runnable, 30)
            }
        }
        handler.postDelayed(runnable, 100)
    }

    private infix fun View.reverseSwipe(anglePair: Pair<Double, Double>) {
        if (this.visibility == View.GONE) {
            this.visibility = View.VISIBLE
        }
        var degree = anglePair.first
        val handler = Handler()
        var runnable: Runnable? = null
        runnable = Runnable {
            if (degree >= anglePair.second) {
                this.x = (centerX!! + (radius * Math.cos(degree * PI / 180))).toFloat() - this.width / 2
                this.y = (centerY!! + (radius * Math.sin(degree * PI / 180))).toFloat() - this.height / 2
                this.rotation = (degree + 270).toFloat()
                degree -= 5
                handler.postDelayed(runnable, 30)
            }
        }
        handler.postDelayed(runnable, 100)
    }

    private infix fun View.bgSwipe2(anglePair: Pair<Double, Double>) {
        var degree = anglePair.first
        val handler = Handler()
        var runnable: Runnable? = null
        runnable = Runnable {
            if (degree <= anglePair.second) {
                this.x = (centerX!! + (radius * Math.cos(degree * PI / 180))).toFloat() - this.width / 2
                this.y = (centerY!! + (radius * Math.sin(degree * PI / 180))).toFloat() - this.height / 2
                this.rotation = (degree + 270).toFloat()
                degree += 5
                handler.postDelayed(runnable, 30)
            }
        }
        handler.postDelayed(runnable, 1)
    }

    private infix fun View.bgSwipe1(anglePair: Pair<Double, Double>) {
        var degree = anglePair.first
        val handler = Handler()
        var runnable: Runnable? = null
        runnable = Runnable {
            if (degree <= anglePair.second) {
                this.x = (centerX!! + (radius * Math.cos(degree * PI / 180))).toFloat() - this.width / 2
                this.y = (centerY!! + (radius * Math.sin(degree * PI / 180))).toFloat() - this.height / 2
                this.rotation = (degree + 270).toFloat()
                degree += 5
                handler.postDelayed(runnable, 30)
            }
        }
        handler.postDelayed(runnable, 201)
    }


    private infix fun View.bgReverseSwipe1(anglePair: Pair<Double, Double>) {
        if (this.visibility == View.GONE) {
            this.visibility = View.VISIBLE
        }
        var degree = anglePair.first
        val handler = Handler()
        var runnable: Runnable? = null
        runnable = Runnable {
            if (degree >= anglePair.second) {
                this.x = (centerX!! + (radius * Math.cos(degree * PI / 180))).toFloat() - this.width / 2
                this.y = (centerY!! + (radius * Math.sin(degree * PI / 180))).toFloat() - this.height / 2
                this.rotation = (degree + 270).toFloat()
                degree -= 5
                handler.postDelayed(runnable, 30)
            }
        }
        handler.postDelayed(runnable, 1)
    }

    private infix fun View.bgReverseSwipe2(anglePair: Pair<Double, Double>) {
        if (this.visibility == View.GONE) {
            this.visibility = View.VISIBLE
        }
        var degree = anglePair.first
        val handler = Handler()
        var runnable: Runnable? = null
        runnable = Runnable {
            if (degree >= anglePair.second) {
                this.x = (centerX!! + (radius * Math.cos(degree * PI / 180))).toFloat() - this.width / 2
                this.y = (centerY!! + (radius * Math.sin(degree * PI / 180))).toFloat() - this.height / 2
                this.rotation = (degree + 270).toFloat()
                degree -= 5
                handler.postDelayed(runnable, 30)
            }
        }
        handler.postDelayed(runnable, 201)
    }

}
