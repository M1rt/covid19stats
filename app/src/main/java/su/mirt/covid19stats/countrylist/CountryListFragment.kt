package su.mirt.covid19stats.countrylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.frag_country_list.countryList
import su.mirt.covid19stats.R

class CountryListFragment : Fragment(R.layout.frag_country_list) {
    val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(CountryListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CountryListAdapter()
        countryList.adapter = adapter
        countryList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.countries.observe(viewLifecycleOwner, Observer<List<Country>> {
            adapter.submitList(it)
        })
    }
}
