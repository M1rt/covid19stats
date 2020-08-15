package su.mirt.covid19stats.util

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapter {
    @BindingAdapter("hidden")
    @JvmStatic
    fun bindHidden(view: View, hidden: Boolean) {
        view.visibility = if (hidden) View.INVISIBLE else View.VISIBLE
    }
}
