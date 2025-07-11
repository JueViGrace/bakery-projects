package com.bakery.database

import com.bakery.database.helper.DbHelper

interface LocalDataSource {
    val dbHelper: DbHelper<*>
}
