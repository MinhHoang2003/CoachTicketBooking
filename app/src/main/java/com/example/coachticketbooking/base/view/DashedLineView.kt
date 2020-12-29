package com.example.coachticketbooking.base.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.coachticketbooking.R


class DashedLineView : View {
    private var density: Float = 1F
    lateinit var paint: Paint
    lateinit var path: Path
    lateinit var effects: PathEffect

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DashedLineView)
        density = typedArray.getFloat(R.styleable.DashedLineView_lineStrokeWidth, 1F)
        paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = density
        //set your own color
        paint.color = ContextCompat.getColor(context, R.color.noti)
        path = Path()
        //array is ON and OFF distances in px (4px line then 2px space)
        effects = DashPathEffect(floatArrayOf(20f, 4f, 20f, 4f), 0F)
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.pathEffect = effects
        val measuredHeight: Int = measuredHeight
        val measuredWidth: Int = measuredWidth
        if (measuredHeight <= measuredWidth) {
            // horizontal
            path.moveTo(0F, 0F)
            path.lineTo(measuredWidth.toFloat(), 0F)
            canvas.drawPath(path, paint)
        } else {
            // vertical
            path.moveTo(0F, 0F)
            path.lineTo(0F, measuredHeight.toFloat())
            canvas.drawPath(path, paint)
        }
    }
}