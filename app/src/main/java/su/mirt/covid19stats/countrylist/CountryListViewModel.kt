package su.mirt.covid19stats.countrylist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import su.mirt.covid19stats.api.ApiClient
import su.mirt.covid19stats.db.database

class CountryListViewModel(app: Application) : AndroidViewModel(app) {
    private val apiClient by lazy { ApiClient.getInstance(Unit) }

    val countries = MediatorLiveData<List<Country>>().also {
        viewModelScope.launch {
            val db = getApplication<Application>().database()
            val countryDao = db.countryDao()
            it.addSource(countryDao.countryListLive()) { countries ->
                it.value = countries.map { Country(it) }
            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) { updateCountries() }
    }

    private suspend fun updateCountries() {
        val db = getApplication<Application>().database()
        val countryDao = db.countryDao()
        val summary = apiClient.summary()
        db.withTransaction {
            summary
                .countries
                .forEach { country ->
                    countryDao.upsert(su.mirt.covid19stats.db.Country(country))
                }
        }
    }
}
