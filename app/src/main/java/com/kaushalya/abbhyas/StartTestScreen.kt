package com.kaushalya.abbhyas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun StartTestScreen(
    navController: NavController,
    viewModel: TestViewModel
) {
    var selected by remember { mutableStateOf(Category.SINGLE_LETTER_CAPITAL) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF176)) // bright yellow
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🎓 Kids Study",
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1976D2)
        )

        Spacer(Modifier.height(40.dp))
        Text(
            text = "Choose Category",
            fontSize = 24.sp
        )

        Spacer(Modifier.height(20.dp))
        Column {
            Category.values().forEach { cat ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { selected = cat },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selected == cat) Color(0xFF4CAF50) else Color.White
                    )
                ) {
                    Text(
                        text = cat.title,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(20.dp),
                        color = if (selected == cat) Color.White else Color.Black
                    )
                }
            }
        }

        Spacer(Modifier.height(50.dp))
        Button(
            onClick = {
                viewModel.startTest(selected)
                navController.navigate("test_screen")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
        ) {
            Text(
                text = "🚀 START THE TEST",
                fontSize = 24.sp,
                color = Color.White
            )
        }

        Spacer(Modifier.height(20.dp))
        TextButton(onClick = { navController.navigate("history") }) {
            Text(
                text = "📜 View Previous Tests",
                fontSize = 18.sp
            )
        }
    }
}