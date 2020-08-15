package su.mirt.covid19stats.countrylist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.frag_country_list.countryList
import su.mirt.covid19stats.R
import java.util.Locale

class CountryListFragment : Fragment(R.layout.frag_country_list) {
    val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(CountryListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.country_list_menu, menu)
        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.countryFilterText.value = newText?.trim()?.toLowerCase(Locale.ROOT) ?: ""
                return true
            }
        })
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
