package su.mirt.covid19stats.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import su.mirt.covid19stats.api.Summary

@Entity(
    tableName = Country.TABLE_NAME,
    indices = [
        Index(value = [Country.NAME], name = Country.NAME_IDX)
    ]
)
data class Country(
    @PrimaryKey
    @ColumnInfo(name = ISO2CODE)
    val iso2code: String,
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = CONFIRMED)
    val totalConfirmed: Int,
    @ColumnInfo(name = RECOVERED)
    val totalRecovered: Int,
    @ColumnInfo(name = DEATHS)
    val totalDeaths: Int
) {
    constructor(country: Summary.Country) : this(
        iso2code = country.iso2code,
        name = country.name,
        totalConfirmed = country.totalConfirmed,
        totalRecovered = country.totalRecovered,
        totalDeaths = country.totalDeaths
    )

    companion object {
        const val TABLE_NAME = "country"
        const val ISO2CODE = "iso"
        const val NAME = "name"
        const val CONFIRMED = "confirmed"
        const val RECOVERED = "recovered"
        const val DEATHS = "deaths"
        const val NAME_IDX = "${NAME}_idx"
    }
}
