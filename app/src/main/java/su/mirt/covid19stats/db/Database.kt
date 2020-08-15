package su.mirt.covid19stats.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import su.mirt.covid19stats.util.SingletonHolder

suspend fun Context.database(): Database =
    coroutineScope {
        withContext(this.coroutineContext + Dispatchers.IO) {
            Database.getInstance(
                this@database
            )
        }
    }

@androidx.room.Database(
    entities = [Country::class],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract fun countryDao(): CountryDao

    companion object : SingletonHolder<Database, Context>({ context ->
        Room.databaseBuilder(context.applicationContext, Database::class.java, "covid19")
            .build()
    })
}
