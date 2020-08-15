package su.mirt.covid19stats.countrylist

import su.mirt.covid19stats.api.Summary
import su.mirt.covid19stats.db.Country

data class Country(
    val name: String,
    val sick: Int,
    val recovered: Int,
    val dead: Int
) {
    constructor(country: Summary.Country) : this(
        name = country.name,
        sick = country.totalConfirmed,
        recovered = country.totalRecovered,
        dead = country.totalDeaths
    )

    constructor(country: Country) : this(
        name = country.name,
        sick = country.totalConfirmed,
        recovered = country.totalRecovered,
        dead = country.totalDeaths
    )
}
