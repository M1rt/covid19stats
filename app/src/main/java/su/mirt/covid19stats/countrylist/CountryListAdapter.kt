package su.mirt.covid19stats.countrylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import su.mirt.covid19stats.R
import su.mirt.covid19stats.databinding.RowCountryBinding

class CountryListAdapter : ListAdapter<Country, CountryListAdapter.ViewHolder>(Companion) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = DataBindingUtil
        .inflate<RowCountryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.row_country,
            parent,
            false
        ).let { ViewHolder(it) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Country, newItem: Country) =
            oldItem == newItem
    }

    class ViewHolder(private val binding: RowCountryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country) {
            binding.country = country
            binding.executePendingBindings()
        }
    }
}
