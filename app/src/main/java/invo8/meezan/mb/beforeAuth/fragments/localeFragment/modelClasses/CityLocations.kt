package invo8.meezan.mb.beforeAuth.fragments.localeFragment.modelClasses


import com.google.gson.annotations.SerializedName

data class CityLocations(
    @SerializedName("country") val country: String? = "",
    @SerializedName("lat") val lat: String? = "",
    @SerializedName("lng") val lng: String? = "",
    @SerializedName("name") val name: String? = ""
)