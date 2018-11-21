package com.example.myview.bezier

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import com.example.myview.MyView

class BezierView : MyView {

    private var startX = 0f
    private var startY = 0f
    private var endX = 0f
    private var endY = 0f
    private var controlX = 0f
    private var controlY = 0f

    private lateinit var startRect: RectF
    private lateinit var endRect: RectF
    private lateinit var controlRect: RectF

    private val path = Path()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun init(context: Context, attrs: AttributeSet) {
        setLayerType(LAYER_TYPE_SOFTWARE, null)

        startRect = RectF(200f, 500f, 250f, 550f)
        startX = (startRect.left + startRect.right) / 2
        startY = (startRect.top + startRect.bottom) / 2


        controlRect = RectF(500f, 300f, 550f, 350f)
        controlX = (controlRect.left + controlRect.right) / 2
        controlY = (controlRect.top + controlRect.bottom) / 2

        endRect = RectF(800f, 500f, 850f, 550f)
        endX = (endRect.left + endRect.right) / 2
        endY = (endRect.top + endRect.bottom) / 2

    }

    override fun widthWrapContent(widthSize: Int, heightSize: Int) {

    }

    override fun heightWrapContent(heightSize: Int, heightSize1: Int) {

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {

            }

            MotionEvent.ACTION_MOVE -> {

            }

            MotionEvent.ACTION_UP -> {

            }

        }
        return true
    }

    override fun drawContent(canvas: Canvas) {
        paint.style = Paint.Style.FILL
        canvas.drawRect(startRect, paint)
        canvas.drawRect(controlRect, paint)
        canvas.drawRect(endRect, paint)

        path.reset()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        path.moveTo(startX, startY)
        path.quadTo(controlX, controlY, endX, endY)
        canvas.drawPath(path, paint)
    }
}