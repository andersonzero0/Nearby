package com.andersonzero0.nearby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.andersonzero0.nearby.data.model.Market
import com.andersonzero0.nearby.ui.screen.home.HomeScreen
import com.andersonzero0.nearby.ui.screen.home.HomeViewModel
import com.andersonzero0.nearby.ui.screen.market_details.MarketDetailsScreen
import com.andersonzero0.nearby.ui.screen.splash.SplashScreen
import com.andersonzero0.nearby.ui.screen.welcome.WelcomeScreen
import com.andersonzero0.nearby.ui.route.Home
import com.andersonzero0.nearby.ui.route.QRCodeScanner
import com.andersonzero0.nearby.ui.route.Splash
import com.andersonzero0.nearby.ui.route.Welcome
import com.andersonzero0.nearby.ui.screen.market_details.MarketDetailsUiEvent
import com.andersonzero0.nearby.ui.screen.market_details.MarketDetailsViewModel
import com.andersonzero0.nearby.ui.screen.qrcode_screen.QRCodeScannerScreen
import com.andersonzero0.nearby.ui.theme.NearbyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NearbyTheme {
                val navController = rememberNavController()

                val homeViewModel by viewModels<HomeViewModel>()
                val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()

                val marketDetailsViewModel by viewModels<MarketDetailsViewModel>()
                val marketDetailsUiState by marketDetailsViewModel.uiState.collectAsStateWithLifecycle()


                NavHost(
                    navController = navController,
                    startDestination = Splash,
                    modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)
                ) {
                    composable<Splash> {
                        SplashScreen(
                            onNavigateToWelcome = {
                                navController.navigate(Welcome)
                            }
                        )
                    }

                    composable<Welcome> {
                        WelcomeScreen(onNavigateToHome = {
                            navController.navigate(Home)
                        })
                    }

                    composable<Home> {
                        HomeScreen(
                            onNavigateToMarketDetails = { selectedMarket ->
                                navController.navigate(selectedMarket)
                            },
                            uiState = homeUiState,
                            onEvent = homeViewModel::onEvent
                        )
                    }

                    composable<Market> {
                        val selectedMarket = it.toRoute<Market>()

                        MarketDetailsScreen(
                            market = selectedMarket,
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                            uiState = marketDetailsUiState,
                            onEvent = marketDetailsViewModel::onEvent,
                            onNavigateToQRCodeScanner = {
                                navController.navigate(QRCodeScanner)
                            }
                        )
                    }

                    composable<QRCodeScanner> {
                        QRCodeScannerScreen(
                            onCompletedScreen = { qrCodeContext ->
                                if (qrCodeContext.isNotEmpty())
                                    marketDetailsViewModel.onEvent(
                                        MarketDetailsUiEvent.OnFetchCoupon(
                                            qrCodeContent = qrCodeContext
                                        )
                                    )
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
