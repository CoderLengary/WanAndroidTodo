/*
 * Copyright (c) 2018 CoderLengary
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.lengary_l.wanandroidtodo.component

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.animation.LinearInterpolator

/**
 * Created by CoderLengary
 */
class WaveView : View {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet):super(context, attrs) {
        init()
    }

    private var mScreenHeight: Int = 0
    private var mScreenWidth: Int = 0

    private val mWavePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private  var mWaveLength: Float =0f

    private var mWavePath: Path = Path()

    private var mOffset: Float = 0f

    private var mWaveCount: Int = 0

    private var mWaveAmplitude: Float = 0f

    private val mWaveColor = -0x1000000

    private lateinit var mValueAnimator: ValueAnimator

    private fun init() {
        mWaveAmplitude = dp2px(10).toFloat()
        mWaveLength = dp2px(500).toFloat()
        initPaint()
    }

    private fun initPaint() {
        with(mWavePaint) {
            color = mWaveColor
            style = Paint.Style.FILL_AND_STROKE
            isAntiAlias = true
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //这种波形宽度一般都是充满屏幕的。所以命名为mScreenWidth
        mScreenHeight = h
        mScreenWidth = w

        //这是为了能够至少产生两个波形，一个在屏幕外，一个在屏幕内
        //如果mWaveLength较小，那么会产生多个波形，一个在屏幕外，其它的全部在屏幕内，见onDraw方法
        mWaveCount = Math.round(mScreenWidth / mWaveLength + 1.5).toInt()

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mWavePath.reset()

        //我们从屏幕外的波形开始画起，这是起始点坐标
        mWavePath.moveTo(-mWaveLength + mOffset, mWaveAmplitude)

        //根据屏幕宽度产生的波形的个数，我们开始定点
        for (i in 0..mWaveCount) {

            //前两个参数是控制点坐标(需根据公式计算)，后两个是结束点坐标(这个坐标是下一行代码的起始点坐标)
            mWavePath.quadTo(-mWaveLength * 3 / 4 + mOffset + i*mWaveLength, -mWaveAmplitude,
                    -mWaveLength/2 + mOffset + i*mWaveLength, mWaveAmplitude)

            //前两个参数是控制点坐标(需根据公式计算)，后两个是结束点坐标
            mWavePath.quadTo(-mWaveLength / 4 + mOffset + i*mWaveLength, 3 * mWaveAmplitude,
                    0f + mOffset + i*mWaveLength, mWaveAmplitude)
        }
        mWavePath.lineTo(width.toFloat(), height.toFloat())
        mWavePath.lineTo(0f, height.toFloat())

        mWavePath.close()

        canvas?.drawPath(mWavePath, mWavePaint)

    }

    private fun initAnimation() {
        mValueAnimator = ValueAnimator.ofFloat(0f, mWaveLength)
        with(mValueAnimator) {
            duration = 2000
            startDelay = 300
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
        }
        mValueAnimator.addUpdateListener {
            mOffset = it.animatedValue as Float
            invalidate()
        }
        mValueAnimator.start()
    }

    fun startPlay() {
       initAnimation()
    }

    fun stopPlay() {
        mValueAnimator.cancel()
    }

    private fun dp2px(dpVal: Int): Int =
         TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal.toFloat(), resources.displayMetrics).toInt()


}