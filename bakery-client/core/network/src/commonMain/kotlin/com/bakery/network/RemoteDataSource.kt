package com.bakery.network

import com.bakery.network.client.base.NetworkClient

interface RemoteDataSource {
    val client: NetworkClient
}
