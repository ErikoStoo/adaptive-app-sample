package com.example.adaptivesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.adaptivesample.ui.theme.AdaptiveSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // It tracks destination of navigation.
            var currentDestination by rememberSaveable {
                mutableStateOf(AppDestinations.HOME)
            }

            val adaptiveInfo = currentWindowAdaptiveInfo()
            val customNavSuiteType = with(adaptiveInfo) {
                if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
                    NavigationSuiteType.NavigationDrawer
                } else {
                    NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
                }
            }

            AdaptiveSampleTheme {
                // Definition of navigation UI.
                NavigationSuiteScaffold(
                    layoutType = customNavSuiteType,
                    navigationSuiteItems = {
                        AppDestinations.entries.forEach {
                            this.item(
                                icon = {
                                    Icon(it.icon, contentDescription = null)
                                },
                                label = { Text(text = it.name) },
                                selected = it == currentDestination,
                                onClick = { currentDestination = it }
                            )
                        }
                    }
                ) {
                    DummyPane(text = "It is ${currentDestination.name} pane.")

                }

            }
        }
    }
}


@Composable
fun DummyPane(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = text)
    }
}

// Item of navigation suite.
enum class AppDestinations(
    val icon: ImageVector
) {
    HOME(Icons.Default.Home),
    FAVORITES(Icons.Default.Favorite),
    SHOPPING(Icons.Default.ShoppingCart),
    PROFILE(Icons.Default.AccountBox),
}

