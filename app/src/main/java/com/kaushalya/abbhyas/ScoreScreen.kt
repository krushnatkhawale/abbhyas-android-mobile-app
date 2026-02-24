package com.kaushalya.abbhyas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ScoreScreen(
    score: Int,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🎉 Test Complete!",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1976D2)
        )

        Spacer(Modifier.height(30.dp))

        Text(
            text = "$score / 10",
            fontSize = 110.sp,
            fontWeight = FontWeight.Bold,
            color = if (score >= 8) Color(0xFF4CAF50) else Color(0xFFFF9800)
        )

        Text(
            text = when {
                score >= 9 -> "Outstanding! 🌟 Super Kid!"
                score >= 7 -> "Great Job! 👍 Keep it up!"
                score >= 5 -> "Good Effort! 💪"
                else -> "Practice more! You can do it! 🚀"
            },
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(60.dp))

        Button(
            onClick = { 
                navController.navigate("start") {
                    popUpTo("start") { saveState = true }
                }
            },
            modifier = Modifier.height(70.dp)
        ) {
            Text("New Test", fontSize = 22.sp)
        }

        Spacer(Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate("history") }) {
            Text("📜 View History", fontSize = 18.sp)
        }
    }
}