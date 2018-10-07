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

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

/**
 * Created by CoderLengary
 */
class AnimationButton: View {


    private var mWidth = 0f
    private var mHeight = 0f

    private var mCircleAngle = 0f

    private var mDefaultTwoCircleDistance = 0f

    private var mCurrentTwoCircleDistance = 0f

    private val mBackGroundColor = -0x4382ad

    private val mText = "Todo."

    private val moveToUpDistance = 300

    private val mPaint = Paint()

    private val mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val mOkTextPaint = Paint()

    private val mOkPath = Path()

    private val mOkPathMeasure = PathMeasure()

    private val mTextRect = Rect()

    private val mRectF = RectF()

    private val mDuration = 1000L

    private lateinit var animator_rect_to_angle: ValueAnimator

    private lateinit var animator_rect_to_square: ValueAnimator

    private lateinit var animator_move_to_up: ObjectAnimator

    private lateinit var animator_draw_ok: ValueAnimator

    private var startDrawOk = false

    private val animationSet = AnimatorSet()

    private var mListener: AnimationButtonListener?= null

    private var isCancel = false

    constructor(context: Context):super(context)

    constructor(context: Context, attr: AttributeSet):super(context, attr){
        initPaint()
    }

    private fun initPaint() {
        with(mPaint) {
            strokeWidth = 4f
            style = Paint.Style.FILL
            isAntiAlias = true
            color = mBackGroundColor
        }

        with(mTextPaint) {
            textSize = 40f
            color = Color.WHITE
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }

        with(mOkTextPaint) {
            strokeWidth = 10f
            style = Paint.Style.STROKE
            isAntiAlias = true
            color = Color.WHITE
        }
    }

    private fun initAnimator() {
        set_rect_to_angle_animation()
        set_rect_to_circle_animation()
        set_move_to_up_animation()
        set_draw_ok_animation()

        animationSet.play(animator_move_to_up)
                .before(animator_draw_ok)
                .after(animator_rect_to_angle)
                .after(animator_rect_to_square)

        animationSet.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                mListener?.let {
                    if (!isCancel) {
                        it.animationFinish()
                    }

                }
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {
            }

        })
    }

    fun start() {
        animationSet.start()
    }

    fun stop() {
        isCancel = true
        animationSet.cancel()

    }




    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()

        mDefaultTwoCircleDistance = (w - h)/ 2.toFloat()

        initOk()
        //必须把initAnimator放在onSizeChanged后面，否则mHeight = 0
        initAnimator()
        animationSet.start()

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            drawRect(it)
            drawText(it)
        }
        if (startDrawOk) {
            canvas?.drawPath(mOkPath, mOkTextPaint)
        }
    }

    private fun drawRect(canvas: Canvas) {
        mRectF.left = mCurrentTwoCircleDistance
        mRectF.top = 0f
        mRectF.right = mWidth - mCurrentTwoCircleDistance
        mRectF.bottom = mHeight
        canvas.drawRoundRect(mRectF, mCircleAngle, mCircleAngle, mPaint)

    }

    private fun drawText(canvas: Canvas) {
        mTextRect.left = 0
        mTextRect.top = 0
        mTextRect.right = mWidth.toInt()
        mTextRect.bottom = mHeight.toInt()
        val fontMetrics = mTextPaint.fontMetricsInt
        val baseline = (mTextRect.bottom + mTextRect.top - fontMetrics.bottom - fontMetrics.top) / 2

        canvas.drawText(mText, mTextRect.centerX().toFloat(), baseline.toFloat(), mTextPaint)
    }

    private fun initOk() {
        mOkPath.moveTo((mDefaultTwoCircleDistance + height / 8 * 3).toFloat(), (height / 2).toFloat())
        mOkPath.lineTo((mDefaultTwoCircleDistance + height / 2).toFloat(), (height / 5 * 3).toFloat())
        mOkPath.lineTo((mDefaultTwoCircleDistance + height / 3 * 2).toFloat(), (height / 5 * 2).toFloat())
        mOkPathMeasure.setPath(mOkPath, true)

    }


    private fun set_rect_to_angle_animation() {
        animator_rect_to_angle = ValueAnimator.ofFloat(0f, mHeight/2)
        animator_rect_to_angle.duration = mDuration
        animator_rect_to_angle.addUpdateListener {
            mCircleAngle = it.animatedValue as Float

            invalidate()
        }
    }

    private fun set_rect_to_circle_animation() {
        animator_rect_to_square = ValueAnimator.ofFloat(0f, mDefaultTwoCircleDistance)
        animator_rect_to_square.duration = mDuration
        animator_rect_to_square.addUpdateListener {
            mCurrentTwoCircleDistance = it.animatedValue as Float
            val alpha = 255 - mCurrentTwoCircleDistance * 255 / mDefaultTwoCircleDistance
            mTextPaint.alpha = alpha.toInt()
        }
    }

    private fun set_move_to_up_animation() {
        val curTranslationY = translationY
        animator_move_to_up = ObjectAnimator.ofFloat(this, "translationY", curTranslationY, curTranslationY - moveToUpDistance)
        animator_move_to_up.duration = mDuration
        animator_move_to_up.interpolator = AccelerateDecelerateInterpolator()
    }

    private fun set_draw_ok_animation() {
        animator_draw_ok = ValueAnimator.ofFloat(1f, 0f)
        animator_draw_ok.duration = mDuration
        animator_draw_ok.addUpdateListener {
            startDrawOk = true
            val value = it.animatedValue as Float
            val effect = DashPathEffect(arrayOf(mOkPathMeasure.length, mOkPathMeasure.length).toFloatArray(), value*mOkPathMeasure.length)
            mOkTextPaint.pathEffect = effect
            invalidate()
        }
    }


    interface AnimationButtonListener {

        fun animationFinish()


    }

    fun setAnimationButtonListener(listener: AnimationButtonListener) {
        mListener = listener
    }
}