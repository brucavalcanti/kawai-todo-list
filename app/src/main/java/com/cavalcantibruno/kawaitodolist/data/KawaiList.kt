package com.cavalcantibruno.kawaitodolist.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class KawaiList(
    val name:String= "",
    val kawaiTheme:String ="",
): Parcelable
