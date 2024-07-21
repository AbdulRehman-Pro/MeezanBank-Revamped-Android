package invo8.meezan.mb.apiClasses

object ApiService {
    private const val url = "http://api.aladhan.com/v1/"

    fun getApiService(): RetrofitService = RetrofitClient()
        .retrofitClient(url)
        .create(RetrofitService::class.java)


}