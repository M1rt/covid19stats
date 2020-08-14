package su.mirt.covid19stats.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Calendar

@Serializable
data class Summary(
    @SerialName("Global")
    val global: Global,
    @SerialName("Countries")
    val countries: List<Country>,
    @Serializable(with = CalendarSerializer::class)
    @SerialName("Date")
    val date: Calendar
) {
    @Serializable
    data class Country(
        @SerialName("Country")
        val name: String,
        @SerialName("CountryCode")
        val iso2code: String,
        @SerialName("Slug")
        val slug: String,
        @SerialName("NewConfirmed")
        val newConfirmed: Int,
        @SerialName("TotalConfirmed")
        val totalConfirmed: Int,
        @SerialName("NewDeaths")
        val newDeaths: Int,
        @SerialName("TotalDeaths")
        val totalDeaths: Int,
        @SerialName("NewRecovered")
        val newRecovered: Int,
        @SerialName("TotalRecovered")
        val totalRecovered: Int,
        @Serializable(with = CalendarSerializer::class)
        @SerialName("Date")
        val date: Calendar
    )

    @Serializable
    data class Global(
        @SerialName("NewConfirmed")
        val newConfirmed: Int,
        @SerialName("TotalConfirmed")
        val totalConfirmed: Int,
        @SerialName("NewDeaths")
        val newDeaths: Int,
        @SerialName("TotalDeaths")
        val totalDeaths: Int,
        @SerialName("NewRecovered")
        val newRecovered: Int,
        @SerialName("TotalRecovered")
        val totalRecovered: Int
    )
}
