package com.example.uiexample

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.res.painterResource

@Composable
fun HomeScreen(paddingValues:PaddingValues, scrollState: ScrollState) {
    // Apply the animated offset to the Column
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        repeat(50) { index ->
            Text(text = "Item $index", modifier = Modifier.padding(8.dp))
        }
        Image( painter = painterResource(id = R.drawable.profile), contentDescription = "Profile Image", modifier = Modifier.size(40.dp))
    }

}