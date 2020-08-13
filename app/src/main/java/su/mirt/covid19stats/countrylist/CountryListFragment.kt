package su.mirt.covid19stats.countrylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import su.mirt.covid19stats.R
import su.mirt.covid19stats.api.ApiClient

class CountryListFragment : Fragment(R.layout.frag_country_list) {
    private val apiClient by lazy { ApiClient() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val summary = apiClient.summary()
            println(summary)
        }
    }
}
