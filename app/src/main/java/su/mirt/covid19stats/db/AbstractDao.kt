package su.mirt.covid19stats.db

import android.database.sqlite.SQLiteConstraintException

abstract class AbstractDao<T> : BaseDao<T> {
    fun upsert(obj: T) = try {
        insert(obj)
    } catch (e: SQLiteConstraintException) {
        update(obj)
    }
}
