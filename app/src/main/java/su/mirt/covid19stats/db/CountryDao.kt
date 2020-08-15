package su.mirt.covid19stats.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
abstract class CountryDao : AbstractDao<Country>() {
    @Query("SELECT * FROM ${Country.TABLE_NAME};")
    abstract fun countryListLive(): LiveData<List<Country>>
}
