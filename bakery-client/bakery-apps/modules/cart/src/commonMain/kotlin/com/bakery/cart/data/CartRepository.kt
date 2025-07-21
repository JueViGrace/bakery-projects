package com.bakery.cart.data

import com.bakery.core.data.Repository

interface CartRepository : Repository

class DefaultCartRepository : CartRepository
