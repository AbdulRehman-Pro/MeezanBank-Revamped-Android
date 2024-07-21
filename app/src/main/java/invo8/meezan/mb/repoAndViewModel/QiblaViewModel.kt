package invo8.meezan.mb.repoAndViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import invo8.meezan.mb.beforeAuth.models.QiblaModel

class QiblaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: QiblaRepository
    val isInternetAvailable: LiveData<Boolean>
    val qiblaResponse: LiveData<QiblaModel>

    init {
        repository = QiblaRepository(application)
        isInternetAvailable = repository.isInternetAvailable
        qiblaResponse = repository.qiblaDirectionData
    }

    fun requestQiblaData(lat: Float, long: Float) {
        repository.qiblaRequest(lat, long)

    }


}