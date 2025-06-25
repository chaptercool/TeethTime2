package com.active.teethtime.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Step(
    @StringRes val stringResourceIdL: Int,
    @DrawableRes val imageResourceId: Int
)
