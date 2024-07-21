package invo8.meezan.mb.repoAndViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import invo8.meezan.mb.apiClasses.ApiService
import invo8.meezan.mb.beforeAuth.models.QiblaModel
import invo8.meezan.mb.common.NetworkConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QiblaRepository(private val application: Application) {

    private val isInternetConnected =
        NetworkConnection(application)
    private var isRequesting: Boolean = false
    val isInternetAvailable = MutableLiveData<Boolean>()
    val qiblaDirectionData = MutableLiveData<QiblaModel>()


    fun qiblaRequest(lat: Float, long: Float) {

        isInternetConnected.observeForever { isConnected ->
            if (isConnected) {
                Log.wtf("Api_Response", "Internet is connected")
                isInternetAvailable.value = true
                fetchDataFromApi(lat, long)
            } else {
                Log.wtf("Api_Response", "Internet is not connected")
                isInternetAvailable.value = false

            }
        }

    }

    private fun fetchDataFromApi(lat: Float, long: Float) {
        if (isInternetConnected.value == true) {
            CoroutineScope(Dispatchers.IO).launch {


                try {

                    Log.wtf(
                        "Current_Location",
                        "Try Block"
                    )

                    val response = ApiService
                        .getApiService()
                        .getQiblaDirection(lat, long)

                    withContext(Dispatchers.Main) {
                        isRequesting = false


                        when {
                            response.isSuccessful -> {
                                Log.wtf("Api_Response", "Success")

                                response.body()?.let { body ->
                                    Log.wtf("Api_Response", body.toString())

                                    qiblaDirectionData.value = response.body()


                                    Log.wtf(
                                        "Api_Response", "Qibla Response ${qiblaDirectionData.value}"
                                    )


                                }
                            }

                            response.code() == 400 -> {
                                Log.wtf("Api_Response", "400 (Not Found)")


                            }

                            response.code() in 500..599 -> {
                                Log.wtf("Api_Response", "500 - 599 (Server Error)")


                            }

                            else -> {
                                Log.wtf("Api_Response", "Unexpected Error")
                            }
                        }


                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        isRequesting = false
                        Log.wtf(
                            "Current_Location",
                            "Catch Block ${e.localizedMessage}"
                        )
                    }
                }
            }
        }
    }


}