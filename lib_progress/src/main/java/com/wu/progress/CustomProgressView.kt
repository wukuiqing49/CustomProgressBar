package com.wu.progress

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import com.wu.progress.utlis.DensityUtil

/**
 *
 * 作者:吴奎庆
 *
 * 时间:2020/9/6
 *
 * 用途: 自定义进度条
 */


class CustomProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    var defultColor: Int? = Color.BLACK
    var progressColor: Int? = Color.BLACK

    var strokeWidth: Float? = 20f
    var contentTextSize: Float? = 15f

    var defultPaint: Paint? = null
    var progressPaint: Paint? = null
    var textPaint: Paint? = null

    var circleDefultPaint: Paint? = null
    var circleProgressPaint: Paint? = null

    var curProgress: Int? = 0;
    var maxProgress: Int? = 100;
    var isCicleTheme: Boolean? = false
    //进度字体的位置
    var textWidth: Float = 0.0f

    init {
        var typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressStyle)
        defultColor = typeArray.getColor(R.styleable.CustomProgressStyle_defultColor, Color.BLACK);
        progressColor =
            typeArray.getColor(R.styleable.CustomProgressStyle_progressColor, Color.RED);
        strokeWidth = typeArray.getDimensionPixelSize(
            R.styleable.CustomProgressStyle_strokeWidth,
            DensityUtil.dpTopx(strokeWidth!!, context).toInt()
        ).toFloat()
        contentTextSize = typeArray.getDimensionPixelSize(
            R.styleable.CustomProgressStyle_contentTextSize,
            DensityUtil.spTopx(contentTextSize!!, context).toInt()
        ).toFloat()
        curProgress = typeArray.getInt(R.styleable.CustomProgressStyle_curProgress, 0);
        maxProgress = typeArray.getInt(R.styleable.CustomProgressStyle_maxProgress, 100);
        if (curProgress!! > maxProgress!!) curProgress = maxProgress
        isCicleTheme = typeArray.getBoolean(R.styleable.CustomProgressStyle_isCicleTheme, true);


        typeArray.recycle()

        defultPaint = Paint()
        defultPaint!!.isAntiAlias = true
        defultPaint!!.color = defultColor!!
        defultPaint!!.style = Paint.Style.STROKE!!
        defultPaint!!.strokeWidth = strokeWidth!!

        progressPaint = Paint()
        progressPaint!!.isAntiAlias = true
        progressPaint!!.strokeWidth = strokeWidth!!
        progressPaint!!.color = defultColor!!
        progressPaint!!.strokeCap = Paint.Cap.ROUND
        progressPaint!!.style = Paint.Style.STROKE!!

        progressPaint = Paint()
        progressPaint!!.isAntiAlias = true
        progressPaint!!.strokeWidth = strokeWidth!!
        progressPaint!!.color = progressColor!!
        progressPaint!!.strokeCap = Paint.Cap.ROUND
        progressPaint!!.style = Paint.Style.STROKE!!

        textPaint = Paint()
        textPaint!!.isAntiAlias = true
        textPaint!!.color = progressColor!!
        textPaint!!.textSize = contentTextSize!!

        circleDefultPaint = Paint()

        circleDefultPaint!!.isAntiAlias = true
        circleDefultPaint!!.strokeWidth = strokeWidth!!
        circleDefultPaint!!.color = defultColor!!
        circleDefultPaint!!.strokeCap = Paint.Cap.ROUND

        circleProgressPaint = Paint()
        circleProgressPaint!!.isAntiAlias = true
        circleProgressPaint!!.strokeWidth = strokeWidth!!
        circleProgressPaint!!.color = progressColor!!
        circleProgressPaint!!.strokeCap = Paint.Cap.ROUND


    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 设置正方形展示
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        if (isCicleTheme!!) {
            setMeasuredDimension(Math.min(widthSize, heightSize), Math.min(widthSize, heightSize))
        }


    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        //半径
        canvas!!.save()
        //圆弧板顶

        if (isCicleTheme!!) {
            drawCircle(canvas)
        } else {
            drawRound(canvas)
        }


    }

    private fun drawCircle(canvas: Canvas?) {
        var radius = (width - strokeWidth!!) / 2
        var rectF = RectF(
            strokeWidth!! / 2!!,
            strokeWidth!! / 2,
            (width - strokeWidth!! / 2!!).toFloat(),
            (width - strokeWidth!! / 2!!).toFloat()
        )

        //画圆
        canvas!!.drawCircle(
            width / 2.toFloat(),
            height / 2.toFloat(),
            radius.toFloat(),
            defultPaint!!
        )
        //绘制 进度弧形
        var b = (curProgress!!.toFloat() / maxProgress!!.toFloat()).toFloat()

        if (curProgress != 0) {
            canvas.drawArc(rectF, 0f, b * 360f, false, progressPaint!!)
        }
        //绘制文字
        var fontMetrics = textPaint!!.fontMetrics;
        var dy = (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent
        var baseLine = width / 2 + dy
        var text = b.toInt().toString() + " %"
        var rect = Rect()
        textPaint!!.getTextBounds(text.toString(), 0, text.length, rect)
        canvas.drawText(text, (width - rect.width()) / 2.toFloat(), baseLine, textPaint!!)
    }


    private fun drawRound(canvas: Canvas?) {
        if (maxProgress == 0) return
        var b = (curProgress!!.toFloat() / maxProgress!!.toFloat()).toFloat()

        var progressHight = strokeWidth!!
        var progressWidth = (b * (width - strokeWidth!! / 2)!!).toFloat()

        canvas!!.drawLine(
            strokeWidth!! / 2,
            progressHight,
            (width - strokeWidth!! / 2).toFloat(),
            progressHight,
            circleDefultPaint!!
        )
        if(curProgress!=0){
            canvas!!.drawLine(
                strokeWidth!! / 2,
                progressHight,
                progressWidth,
                progressHight,
                circleProgressPaint!!
            )
        }

        var prgs = ((curProgress!!.toFloat() / maxProgress!!.toFloat()) * 100).toInt()
        if (prgs > 100){
            prgs = 100
        }else{

        }


        if (b > 0.85) {
            textWidth = (0.85!! * (width - strokeWidth!! / 2) + 60).toFloat()
        } else {
            textWidth = (b!! * (width - strokeWidth!! / 2) + 60).toFloat()
        }

        var text = prgs.toString() + "%"
        var rect = Rect()

        textPaint!!.getTextBounds(text, 0, text.length, rect)
        var textLine = (strokeWidth!! * 2 + 20).toFloat()
        canvas!!.drawText(text, textWidth, textLine, textPaint!!)

    }

    fun setCurProgress(cur: Int) {
        if (cur!! > maxProgress!!) return
        this.curProgress = cur;
        invalidate()
    }

    fun setMaxProgress(max: Int) {

        this.maxProgress = max
    }

    fun getBaseline(p: Paint): Float {
        val fontMetrics: Paint.FontMetrics = p.fontMetrics
        return height / 2 + ((fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent)
    }


}