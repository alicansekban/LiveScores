package com.alican.livescores.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

data class MatchesResponse(

	@Json(name = "success")
	val success: Boolean,

	@Json(name = "data")
	val data: List<MatchResponseModel>,
)


@Parcelize
data class MatchResponseModel(

	@Json(name = "sc")
	val sc: Sc? = null,

	@Json(name = "st")
	val st: String? = null,

	@Json(name = "at")
	val at: At? = null,

	@Json(name = "sgi")
	val sgi: Int? = null,

	@Json(name = "d")
	val d: Long? = null,

	@Json(name = "v")
	val v: String? = null,

	@Json(name = "bri")
	val bri: Int? = null,

	@Json(name = "i")
	val i: Int? = null,

	@Json(name = "to")
	val to: To? = null,

	@Json(name = "ht")
	val ht: Ht? = null
) : Parcelable {
	fun getMatchScore(): String {
		return "${sc?.ht?.r} - ${sc?.at?.r}"
	}
}

@Parcelize
data class Ht(

	@Json(name = "p")
	val p: Int? = null,

	@Json(name = "rc")
	val rc: Int? = null,

	@Json(name = "i")
	val i: Int? = null,

	@Json(name = "sn")
	val sn: String? = null,

	@Json(name = "n")
	val n: String? = null,

	@Json(name = "r")
	val r: Int? = null,

	@Json(name = "c")
	val c: Int? = null,

	@Json(name = "ht")
	val ht: Int? = null
) : Parcelable

@Parcelize
data class To(

	@Json(name = "p")
	val p: Int? = null,

	@Json(name = "flag")
	val flag: String? = null,

	@Json(name = "i")
	val i: Int? = null,

	@Json(name = "sn")
	val sn: String? = null,

	@Json(name = "n")
	val n: String? = null
) : Parcelable

@Parcelize
data class Sc(

	@Json(name = "st")
	val st: Int? = null,

	@Json(name = "at")
	val at: At? = null,

	@Json(name = "abbr")
	val abbr: String? = null,

	@Json(name = "ht")
	val ht: Ht? = null
) : Parcelable

@Parcelize
data class At(

	@Json(name = "p")
	val p: Int? = null,

	@Json(name = "rc")
	val rc: Int? = null,

	@Json(name = "i")
	val i: Int? = null,

	@Json(name = "sn")
	val sn: String? = null,

	@Json(name = "n")
	val n: String? = null,

	@Json(name = "r")
	val r: Int? = null,

	@Json(name = "c")
	val c: Int? = null,

	@Json(name = "ht")
	val ht: Int? = null
) : Parcelable

