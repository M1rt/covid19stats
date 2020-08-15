package su.mirt.covid19stats.countrylist

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import su.mirt.covid19stats.R
import su.mirt.covid19stats.api.ApiClient
import su.mirt.covid19stats.db.database
import java.util.Locale

class CountryListViewModel(app: Application) : AndroidViewModel(app) {
    private val apiClient by lazy { ApiClient.getInstance(Unit) }

    val countryFilterText = MutableLiveData<String>().apply { value = "" }
    val countries = MediatorLiveData<List<Country>>().also { countriesData ->
        var countries: List<Country> = emptyList()
        var filter = ""
        fun update() {
            countriesData.value =
                if (filter.isEmpty()) {
                    countries
                } else {
                    countries.filter {
                        it.name.trim().toLowerCase(Locale.ROOT).indexOf(filter) >= 0
                    }
                }
                    .sortedBy { it.name }
        }

        countriesData.addSource(countryFilterText) { filter = it; update() }

        viewModelScope.launch {
            val db = getApplication<Application>().database()
            val countryDao = db.countryDao()
            countriesData.addSource(countryDao.countryListLive()) { newCountries ->
                countries = newCountries.map { Country(it) }
                update()
            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) { updateCountries() }
    }

    private suspend fun updateCountries() = apiClient
        .summary()
        .onSuccess { summary ->
            val db = getApplication<Application>().database()
            val countryDao = db.countryDao()
            db.withTransaction {
                summary
                    .countries
                    .forEach { country ->
                        countryDao.upsert(su.mirt.covid19stats.db.Country(country))
                    }
            }
            val context = getApplication<Application>().applicationContext
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    context.getString(R.string.data_sync_ok),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        .onFailure {
            val context = getApplication<Application>().applicationContext
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    context.getString(
                        R.string.data_sync_error,
                        it.localizedMessage ?: it.message ?: it.toString()
                    ),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
}
