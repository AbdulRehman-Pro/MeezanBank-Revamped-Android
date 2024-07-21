package invo8.meezan.mb.beforeAuth.models


import com.google.gson.annotations.SerializedName

data class QiblaModel(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("status")
    val status: String?
)
data class Data(
    @SerializedName("direction")
    val direction: Double?,
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?
)