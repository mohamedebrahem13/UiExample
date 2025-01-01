package com.example.uiexample

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun AnimatedContentExample() {
    var textIndex by remember { mutableIntStateOf(0) }

    val texts = listOf("Hello", "Welcome", "Jetpack Compose", "Animations!")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Add padding to the column itself
        verticalArrangement = Arrangement.Center, // Vertically center content
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // AnimatedContent to transition between different content
        AnimatedContent(
            targetState = textIndex,
            transitionSpec = {
                // Define the transitionSpec for enter and exit
                (slideInHorizontally(initialOffsetX = { -300 }) + fadeIn(animationSpec = tween(durationMillis = 1000))).togetherWith(
                    slideOutHorizontally(targetOffsetX = { 300 }) + fadeOut(
                        animationSpec = tween(durationMillis = 1000)
                    )
                )
            },
            label = ""
        ) { targetState ->
            // Text change animation
            Text(
                text = texts[targetState],
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(16.dp)
            )
        }

        // Spacer to create some space between Text and Button
        Spacer(modifier = Modifier.height(32.dp))

        // Button to cycle through the texts
        Button(
            onClick = {
                textIndex = (textIndex + 1) % texts.size
            },
            modifier = Modifier.fillMaxWidth() // Make button width fill the available space
        ) {
            Text("Next Text")
        }
    }
}