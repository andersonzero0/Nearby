package com.andersonzero0.nearby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.andersonzero0.nearby.data.model.Market
import com.andersonzero0.nearby.ui.screen.HomeScreen
import com.andersonzero0.nearby.ui.screen.MarketDetailsScreen
import com.andersonzero0.nearby.ui.screen.SplashScreen
import com.andersonzero0.nearby.ui.screen.WelcomeScreen
import com.andersonzero0.nearby.ui.screen.route.Home
import com.andersonzero0.nearby.ui.screen.route.Splash
import com.andersonzero0.nearby.ui.screen.route.Welcome
import com.andersonzero0.nearby.ui.theme.NearbyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NearbyTheme {
                val navController = rememberNavController()

                Scaffold { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Splash,
                        modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
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
                            HomeScreen(onNavigateToMarketDetails = { selectedMarket ->
                                navController.navigate(selectedMarket)
                            })
                        }

                        composable<Market> {
                            val selectedMarket = it.toRoute<Market>()

                            MarketDetailsScreen(market = selectedMarket, onNavigateBack = {
                                navController.popBackStack()
                            })
                        }
                    }
                }
            }
        }
    }
}
