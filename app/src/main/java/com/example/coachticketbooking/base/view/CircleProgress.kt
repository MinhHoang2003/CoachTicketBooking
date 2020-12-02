package com.example.coachticketbooking.base.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.core.content.ContextCompat
import com.example.coachticketbooking.R

class CircleProgress : View {

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        attrs?.let { initView(it) }
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        attrs?.let { initView(it) }
    }

    companion object {
        private const val MIN_PERCENT_PROGRESS = 0F
        private const val MAX_PERCENT_PROGRESS = 100F
        private const val MIN_ANGLE_PROGRESS = 0F
        private const val MAX_ANGLE_PROGRESS = 360F
        private const val RATIO_PERCENT_WITH_PROGRESS = 3.6F
        private const val SWEEP_ANGLE_COMPLETE_PROGRESS = 75
        private const val SWEEP_ANGLE_PROGRESS = 75
        private const val DURATION_ANIMATION_ROTATION = 1000L
        private const val DURATION_ANIMATION_PROGRESS_CIRCLE = 50L
    }

    private var mSweepAngle = 0F
    private var mStartAngle = 90F
    private var mOval = RectF()
    private val mProgressPaint = Paint()
    private val mIncompletePaint = Paint()
    private var mStrokeWidth = 0F

    private var mHasSetUpAnimation = false
    private var mColorProgressSuccess: Int = 0
    private var mProgressAnimation = ValueAnimator()

    private fun initView(attributeSet: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleProgress)
        mColorProgressSuccess = ContextCompat.getColor(context, R.color.noti)
        val colorIncompleteProgress = ContextCompat.getColor(context, R.color.noti)
        val colorProgress = ContextCompat.getColor(context, android.R.color.transparent)
        mStrokeWidth = resources.getDimension(R.dimen.circle_progress_stroke_with)

        mProgressPaint.apply {
            color = colorProgress
            strokeWidth = mStrokeWidth
            style = Paint.Style.STROKE
            flags = Paint.ANTI_ALIAS_FLAG
        }

        mIncompletePaint.apply {
            color = colorIncompleteProgress
            strokeWidth = mStrokeWidth
            style = Paint.Style.STROKE
            flags = Paint.ANTI_ALIAS_FLAG
            strokeCap = Paint.Cap.ROUND
        }

        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mOval.set(
            mStrokeWidth / 2,
            mStrokeWidth / 2,
            width - mStrokeWidth / 2,
            width - mStrokeWidth / 2
        )
        canvas?.drawArc(
            mOval,
            -mStartAngle + SWEEP_ANGLE_COMPLETE_PROGRESS * RATIO_PERCENT_WITH_PROGRESS,
            SWEEP_ANGLE_PROGRESS * RATIO_PERCENT_WITH_PROGRESS,
            false,
            mProgressPaint
        )

        canvas?.drawArc(
            mOval,
            -mStartAngle,
            SWEEP_ANGLE_PROGRESS * RATIO_PERCENT_WITH_PROGRESS,
            false,
            mIncompletePaint
        )
    }

    private fun startAnimation() {
        val rotationAnimation = RotateAnimation(
            MIN_ANGLE_PROGRESS,
            MAX_ANGLE_PROGRESS,
            Animation.RELATIVE_TO_SELF,
            0.5F,
            Animation.RELATIVE_TO_SELF,
            0.5F
        )

        rotationAnimation.apply {
            duration = DURATION_ANIMATION_ROTATION
            interpolator = LinearInterpolator()
            repeatCount = Animation.INFINITE
            repeatMode = Animation.INFINITE
        }
        this.startAnimation(rotationAnimation)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnimation()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopProgressAnimation()
        mProgressAnimation.removeAllUpdateListeners()
        this.clearAnimation()
    }

    private fun stopProgressAnimation() {
        mProgressAnimation.cancel()
    }
}