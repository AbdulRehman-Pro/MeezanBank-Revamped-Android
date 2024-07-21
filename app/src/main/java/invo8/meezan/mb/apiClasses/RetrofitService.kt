package invo8.meezan.mb.apiClasses

import invo8.meezan.mb.beforeAuth.models.QiblaModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("qibla/{latitude}/{longitude}")
    suspend fun getQiblaDirection(
        @Path("latitude") lat: Float?,
        @Path("longitude") lon: Float?,
    ): Response<QiblaModel>


}