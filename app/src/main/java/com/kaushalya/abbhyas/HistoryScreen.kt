package com.kaushalya.abbhyas

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaushalya.abbhyas.TestViewModel

@Composable
fun HistoryScreen(
    viewModel: TestViewModel
) {
    val context = LocalContext.current
    val history = remember { viewModel.getHistory(context) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "📜 Previous Tests",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        items(history) { entry ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4F8))
            ) {
                Text(
                    text = entry,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(20.dp)
                )
            }
        }

        if (history.isEmpty()) {
            item {
                Text(
                    text = "No previous tests yet.\nStart your first test! 🎯",
                    fontSize = 20.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(32.dp)
                )
            }
        }
    }
}