package com.andersonzero0.nearby.ui.screen.market_details

import com.andersonzero0.nearby.data.model.Rule

data class MarketDetailsUiState(
    val rules: List<Rule>? = null,
    val coupon: String? = null,
)
