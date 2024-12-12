package com.andersonzero0.nearby.ui.screen.home

import com.andersonzero0.nearby.data.model.Category
import com.andersonzero0.nearby.data.model.Market
import com.google.android.gms.maps.model.LatLng

data class HomeUiState(
    val categories: List<Category>? = null,
    val markets: List<Market>? = null,
    val marketLocations: List<LatLng>? = null,
)