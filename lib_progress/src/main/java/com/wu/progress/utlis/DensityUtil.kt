package com.wu.progress.utlis

import android.content.Context
import android.util.TypedValue

/**
 *
 * 作者:吴奎庆
 *
 * 时间:2020/9/6
 *
 * 用途: 像素转换工具类
 */


object DensityUtil {
    /**
     * dp转px
     */
    fun dpTopx(dp :Float,context :Context): Float{
       return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.resources.displayMetrics)
    }

    /**
     * sp转px
     */
    fun spTopx(sp :Float,context :Context): Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,context.resources.displayMetrics)
    }


}