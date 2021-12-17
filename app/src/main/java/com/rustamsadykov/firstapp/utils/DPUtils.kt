package com.rustamsadykov.firstapp.utils

import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.Dimension

fun dpToPx(displayMetrics: DisplayMetrics, @Dimension(unit = Dimension.DP) dp: Float) =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        displayMetrics
    )

