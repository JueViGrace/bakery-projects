package com.bakery.home.data

import com.bakery.database.BakeryDB
import com.bakery.database.helper.DbHelper
import com.bakery.network.client.base.NetworkClient

class DefaultHomeRepository(private val dbHelper: DbHelper<BakeryDB>, private val client: NetworkClient) : HomeRepository
