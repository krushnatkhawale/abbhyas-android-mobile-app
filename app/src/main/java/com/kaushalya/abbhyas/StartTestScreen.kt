package com.kaushalya.abbhyas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun StartTestScreen(
    navController: NavController,
    viewModel: TestViewModel
) {
    var selectedLanguage by remember { mutableStateOf<StudyData.AppLanguage?>(null) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF176))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🎓 Kids Study",
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1976D2)
        )

        Spacer(Modifier.height(16.dp))

        if (selectedLanguage == null) {
            Text("Select Language", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(40.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(StudyData.AppLanguage.values().toList()) { lang ->
                    LanguageCard(lang) { selectedLanguage = lang }
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { selectedLanguage = null; selectedCategory = null }) {
                    Text("←", fontSize = 32.sp)
                }
                Text(
                    text = "${selectedLanguage!!.emoji} ${selectedLanguage!!.displayName}",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(20.dp))
            Text("Choose Activity", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(24.dp))

            val categories = StudyData.getCategoriesForLanguage(selectedLanguage!!)

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat
                    CategoryCard(cat, isSelected) { selectedCategory = cat }
                }
            }

            Spacer(Modifier.height(20.dp))
            Button(
                onClick = {
                    selectedCategory?.let {
                        viewModel.startTest(it)
                        navController.navigate("test_screen")
                    }
                },
                enabled = selectedCategory != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
            ) {
                Text("🚀 START THE TEST", fontSize = 24.sp, color = Color.White)
            }
        }
    }
}

@Composable
private fun LanguageCard(language: StudyData.AppLanguage, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color(language.color)),
        elevation = CardDefaults.cardElevation(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(language.emoji, fontSize = 60.sp)
            Spacer(Modifier.width(24.dp))
            Text(
                language.displayName,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun CategoryCard(category: Category, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFF4CAF50) else Color.White
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(getCategoryEmoji(category), fontSize = 48.sp)
            Spacer(Modifier.height(12.dp))
            Text(
                text = category.displayName,   // ← now shows मराठी / हिंदी text for parents
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Color.White else Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
        }
    }
}

private fun getCategoryEmoji(category: Category): String = when (category) {
    Category.SINGLE_LETTER_CAPITAL, Category.SINGLE_LETTER_SMALL -> "🔠"
    Category.TWO_LETTER_CAPITAL, Category.TWO_LETTER_SMALL -> "🔤"
    Category.THREE_LETTER_CAPITAL, Category.THREE_LETTER_SMALL -> "📝"
    Category.MARATHI_VARNMALA, Category.TWO_LETTER_MARATHI, Category.THREE_LETTER_MARATHI -> "🇮🇳"
    Category.HINDI_VARNMALA, Category.TWO_LETTER_HINDI, Category.THREE_LETTER_HINDI -> "🕉️"
    Category.NUMBERS -> "🔢"
}