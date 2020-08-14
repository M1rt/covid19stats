package su.mirt.covid19stats.countrylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import su.mirt.covid19stats.api.ApiClient

class CountryListViewModel : ViewModel() {
    private val apiClient by lazy { ApiClient.getInstance(Unit) }

    private val countries_ = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> = countries_

    init {
        viewModelScope.launch(Dispatchers.Main) { updateCountries() }
    }

    private suspend fun updateCountries() {
        countries_.value = withContext(Dispatchers.IO) {
            apiClient.summary()
                .countries
                .map { Country(it) }
        }
    }
}
