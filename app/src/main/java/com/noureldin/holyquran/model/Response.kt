package com.noureldin.holyquran.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Response(

	@field:SerializedName("radios")
	val radios: List<Radio>? = null
) : Parcelable