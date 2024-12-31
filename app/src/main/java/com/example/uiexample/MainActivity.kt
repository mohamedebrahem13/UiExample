package com.example.uiexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uiexample.ui.theme.UiExampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UiExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val (searchQuery, setSearchQuery) = remember { mutableStateOf("") }
    val (selectedItem, setSelectedItem) = remember { mutableStateOf("Home") }

    // Scroll state to track the scroll position
    val scrollState = rememberScrollState()

    // Offset calculation for the AppBar based on the scroll position
    val topBarOffset = remember { mutableFloatStateOf(0f) }
    val bottomBarOffset = remember { mutableFloatStateOf(0f) }

    // Track the previous scroll value to detect direction
    var previousScrollValue by remember { mutableIntStateOf(0) }

    // Update the offset based on the scroll state
    LaunchedEffect(scrollState.value) {
        val scrollDirection = if (scrollState.value > previousScrollValue) "down" else "up"

        if (scrollDirection == "down") {
            // Hide the bars when scrolling down
            topBarOffset.floatValue = minOf(scrollState.value.toFloat(), 200f) // Limit the top bar offset to 200dp
            bottomBarOffset.floatValue = minOf(scrollState.value.toFloat(), 80f) // Limit the bottom bar offset to 80dp
        } else {
            // Show the bars when scrolling up
            topBarOffset.floatValue = 0f
            bottomBarOffset.floatValue = 0f
        }

        // Update previous scroll value to current scroll value
        previousScrollValue = scrollState.value
    }
    // Animate the top and bottom bar offsets
    val animatedTopBarOffset by animateDpAsState(
        targetValue = (-topBarOffset.floatValue).dp,
        label = "TopBarAnimation"
    )
    val animatedBottomBarOffset by animateDpAsState(
        targetValue = bottomBarOffset.floatValue.dp,
        label = "BottomBarAnimation"
    )

    // Calculate window height and bottom bar height
    val windowHeightInDp = getWindowHeightInDp()
    val dynamicHeight = windowHeightInDp * 0.08f


    Scaffold(
        topBar = {
            // CustomAppBar with animated offset
            CustomAppBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = setSearchQuery,
                height = dynamicHeight,
                offset = animatedTopBarOffset
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedItem = selectedItem,
                offset = animatedBottomBarOffset,
                height = dynamicHeight,
                onItemSelected = { setSelectedItem(it) }
            )
        },
        content = { paddingValues ->

            // Render content based on the selectedItem
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
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    UiExampleTheme {
        MainScreen()
    }
}