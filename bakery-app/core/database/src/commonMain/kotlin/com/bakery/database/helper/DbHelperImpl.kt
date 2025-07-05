package com.bakery.database.helper

import com.bakery.database.BakeryDB

class DbHelperImpl(override val db: BakeryDB) : DbHelper<BakeryDB>
