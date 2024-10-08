package com.example.newsly.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NewsDetailsButton(painter: Painter, content: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(0.dp)
            .height(60.dp)
            .wrapContentWidth()
            //.width(150.dp)
            .clickable{
                onClick()
            },
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            // contentColor = Color(0xFF5f0f40)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 5.dp)
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {

           // Spacer(modifier = Modifier.width(10.dp))

                Icon(
                    painter,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier
                        .size(35.dp)
                        .padding(1.dp)
                )

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = content,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
            )
        }
    }
}