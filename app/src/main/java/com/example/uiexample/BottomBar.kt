package com.example.uiexample

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset

@Composable
fun BottomNavigationBar(
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    offset: Dp, // Animated offset
    height: Dp
) {
    // Animate the offset using animateDpAsState for smooth transitions
    val animatedOffset by animateDpAsState(targetValue = (offset), label = "")
    val items = listOf(
        BottomNavItem("Home", R.drawable.baseline_warehouse_24),
        BottomNavItem("Search", R.drawable.baseline_youtube_searched_for_24),
        BottomNavItem("Profile", R.drawable.baseline_accessibility_new_24)
    )

    NavigationBar(
        modifier = Modifier.offset { IntOffset(0, animatedOffset.roundToPx()) }
            .height(height) // Apply dynamic height
            .offset { IntOffset(0, offset.roundToPx()) }, // Apply animated offset for sliding effect
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItem == item.label,
                onClick = { onItemSelected(item.label) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconResId),
                        contentDescription = item.label
                    )
                },
                label = { Text(text = item.label) },
                alwaysShowLabel = true
            )
        }

    }

}



data class BottomNavItem(val label: String, val iconResId: Int)
