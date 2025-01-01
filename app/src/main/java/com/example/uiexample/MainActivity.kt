package com.example.uiexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.uiexample.ui.theme.UiExampleTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UiExampleTheme {
                val windowSizeClass = calculateWindowSizeClass(this)

                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AnimatedContentExample()                }
            }
        }
    }
}

@Composable
fun MainScreen(windowSizeClass: WindowSizeClass) {
    val (searchQuery, setSearchQuery) = remember { mutableStateOf("") }
    val (selectedItem, setSelectedItem) = remember { mutableStateOf("Home") }

    val scrollState = rememberScrollState()
    val isTablet = windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Medium

    var topBarOffset by remember { mutableStateOf(0.dp) }
    var bottomBarOffset by remember { mutableStateOf(0.dp) }
    var previousScrollValue by remember { mutableIntStateOf(0) }

    LaunchedEffect(scrollState.value) {
        if (scrollState.value > previousScrollValue) {
            // User is scrolling down - hide the bars
            topBarOffset = (-64).dp
            bottomBarOffset = 80.dp
        } else {
            // User is scrolling up - show the bars
            topBarOffset = 0.dp
            bottomBarOffset = 0.dp
        }
        previousScrollValue = scrollState.value
    }

    // Animated offsets
    val animatedTopBarOffset by animateDpAsState(targetValue = topBarOffset, label = "TopBarAnimation")
    val animatedBottomBarOffset by animateDpAsState(targetValue = bottomBarOffset, label = "BottomBarAnimation")

    Scaffold(
        topBar = {
            // Always show the top bar, regardless of device type (phone or tablet)
            CustomAppBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = setSearchQuery,
                height = 56.dp,
                offset = animatedTopBarOffset // Apply animated offset
            )
        },
        bottomBar = {
            // Always show the bottom bar, regardless of device type (phone or tablet)
            BottomNavigationBar(
                selectedItem = selectedItem,
                height = 56.dp,
                offset = animatedBottomBarOffset,
                onItemSelected = setSelectedItem
            )
        },
        content = { paddingValues ->
            if (isTablet) {
                // Handle large screen layouts with two-pane-like behavior
                Row(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.weight(1f)) {
                        HomeScreen(paddingValues, scrollState = scrollState)
                    }
                    Spacer(modifier = Modifier.width(16.dp)) // Add space between panes
                    Column(modifier = Modifier.weight(1f)) {
                        ProfileScreen(paddingValues)
                    }
                }
            } else {
                when (selectedItem) {
                    "Home" -> HomeScreen(paddingValues, scrollState = scrollState)
                    "Search" -> SearchScreen(paddingValues)
                    "Profile" -> ProfileScreen(paddingValues)
                    else -> Text(
                        text = "Unknown Screen",
                        modifier = Modifier.padding(paddingValues).fillMaxSize()
                    )
                }
            }
        }
    )
}
