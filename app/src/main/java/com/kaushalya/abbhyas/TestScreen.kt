package com.kaushalya.abbhyas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TestScreen(
    viewModel: TestViewModel,
    navController: NavController
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel.isComplete) {
        if (viewModel.isComplete) {
            viewModel.saveHistory(context)
            navController.navigate("score/${viewModel.score}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFE0))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Question counter
        Text(
            text = "Question ${viewModel.currentIndex + 1} of 10",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1976D2)
        )

        Spacer(Modifier.height(24.dp))

        // Timer
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = viewModel.timeLeft / 10f,
                strokeWidth = 14.dp,
                modifier = Modifier.size(130.dp),
                color = Color(0xFF4CAF50)
            )
            Text(
                text = "${viewModel.timeLeft}s",
                fontSize = 52.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1976D2)
            )
        }

        Spacer(Modifier.height(40.dp))

        // === HUGE QUESTION DISPLAY (always visible after start) ===
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                val currentQuestion = if (viewModel.questions.isNotEmpty() && viewModel.currentIndex in viewModel.questions.indices) {
                    viewModel.questions[viewModel.currentIndex]
                } else {
                    "Starting..." // Brief placeholder only on initial load
                }
                Text(
                    text = currentQuestion,
                    fontSize = 160.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2),
                    textAlign = TextAlign.Center,
                    lineHeight = 160.sp
                )
            }
        }

        Spacer(Modifier.height(40.dp))

        // Parent buttons (big & easy to tap)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { viewModel.markCorrect() },
                modifier = Modifier
                    .weight(1f)
                    .height(85.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("✅ Kid Got It!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = { viewModel.markIncorrect() },
                modifier = Modifier
                    .weight(1f)
                    .height(85.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
            ) {
                Text("❌ Not Yet", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}