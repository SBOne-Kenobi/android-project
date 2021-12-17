package com.rustamsadykov.firstapp.utils.extentions

import android.view.View
import androidx.annotation.Dimension
import com.rustamsadykov.firstapp.utils.dpToPx

fun View.dpToPx(@Dimension(unit = Dimension.DP) dp: Float): Float =
    dpToPx(resources.displayMetrics, dp)