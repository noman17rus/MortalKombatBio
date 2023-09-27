package com.example.mortalkombatbio.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Hero(
    @StringRes val name: Int,
    @StringRes val bio: Int,
    @DrawableRes val image: Int
)