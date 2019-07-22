package com.aakash.arcslideview

import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Display
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.PI

class MainActivity : AppCompatActivity() {

    private var centerX: Float? = null
    private var centerY: Float? = null

    private var radius: Double = 0.0

    private var maxScreenWidth: Int = 0
//    private var preX: Float = 0f

    private var contentPreAngle: Double = 0.0
    private var content2PreAngle: Double = 0.0
//    var direction = -1
//
//    companion object {
//        private const val DIR_LEFT = 0
//        private const val DIR_RIGHT = 1
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val display: Display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        maxScreenWidth = size.x

        centerX = (maxScreenWidth / 2).minus(80).toFloat()
        centerY = 0f

        radius = (maxScreenWidth / 2).toDouble()

//        rlParent.setOnTouchListener { v, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    preX = event.x
//                    contentPreAngle = 180.0
//                    content2PreAngle = 90.0
//                    setupTextLocation()
//                }
//                MotionEvent.ACTION_MOVE -> {
//                    println("event = ${event.x}")
//                    if (event.x - preX >= (maxScreenWidth / 9)) {
//                        preX = event.x
//                        contentPreAngle -= 5
//                        content2PreAngle -= 5
//                        direction = DIR_RIGHT
//                        setupTextLocation()
//                    }
////                    if (preX - event.x >= (maxScreenWidth / 9)) {
////                        preX = event.x
////                        contentPreAngle += 5
////                        content2PreAngle += 5
////                        direction = DIR_LEFT
////                        setupTextLocation()
////                    }
//                }
//                MotionEvent.ACTION_UP -> {
//                    when (direction) {
//                        DIR_RIGHT -> {
//                            tvContent reverseSwipe Pair(contentPreAngle, 90.0)
//                            tvContent2 reverseSwipe Pair(content2PreAngle, 0.0)
//                        }
////                        DIR_LEFT -> {
////                            tvContent swipe Pair(contentPreAngle, 90.0)
////                            tvContent2 swipe Pair(content2PreAngle, 180.0)
////                        }
//                    }
//                }
//            }
//            true
//        }

        rlParent.setOnTouchListener (object : OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeRight() {
                println("MainActivity.onSwipeRight")
                tvContent reverseSwipe Pair(180.0, 90.0)
                tvContent2 reverseSwipe Pair(90.0, 0.0)
            }

            override fun onSwipeLeft() {
                println("MainActivity.onSwipeLeft")
                tvContent swipe Pair(90.0, 180.0)
                tvContent2 swipe Pair(0.0, 90.0)
            }
        })

    }

    private fun setupTextLocation() {
        tvContent setLocation contentPreAngle
        tvContent2 setLocation content2PreAngle
    }

    private infix fun View.setLocation(degree: Double) {
        this.x = (centerX!! + (radius * Math.cos(degree * PI / 180))).toFloat()
        this.y = (centerY!! + (radius * Math.sin(degree * PI / 180))).toFloat()
        this.rotation = (degree + 270).toFloat()
    }

    private infix fun View.swipe(anglePair: Pair<Double, Double>) {
        var degree = anglePair.first
        val handler = Handler()
        var runnable: Runnable? = null
        runnable = Runnable {
            if (degree <= anglePair.second) {
                this.x = (centerX!! + (radius * Math.cos(degree * PI / 180))).toFloat()
                this.y = (centerY!! + (radius * Math.sin(degree * PI / 180))).toFloat()
                this.rotation = (degree + 270).toFloat()
                degree += 10
                handler.postDelayed(runnable, 10)
            }
        }
        handler.postDelayed(runnable, 100)
    }

    private infix fun View.reverseSwipe(anglePair: Pair<Double, Double>) {
        var degree = anglePair.first
        val handler = Handler()
        var runnable: Runnable? = null
        runnable = Runnable {
            if (degree >= anglePair.second) {
                this.x = (centerX!! + (radius * Math.cos(degree * PI / 180))).toFloat()
                this.y = (centerY!! + (radius * Math.sin(degree * PI / 180))).toFloat()
                this.rotation = (degree + 270).toFloat()
                degree -= 10
                handler.postDelayed(runnable, 10)
            }
        }
        handler.postDelayed(runnable, 100)
    }

}
