package com.example.newsly.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsly.domain.model.News
import com.example.newsly.domain.model.Source
import com.example.newsly.ui.theme.NewslyTheme
import com.example.newsly.ui.theme.spacing
import com.loc.newsapp.presentation.Dimens.ArticleCardSize



@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    news: News,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth() // Ensure card takes full width in the grid
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(ArticleCardSize)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(context).data(news.urlToImage).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(vertical = MaterialTheme.spacing.extraSmall)
        ) {
            Text(
                text = news.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.tertiaryContainer,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = MaterialTheme.spacing.small)
            ) {
                Text(
                    text = news.source.name,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                Icon(
                    imageVector = Icons.Filled.AccessTime,
                    contentDescription = null,
                    modifier = Modifier.size(MaterialTheme.spacing.medium),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                Text(
                    text = news.publishedAt,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardPreview() {
    NewslyTheme {
        NewsCard(
            news = News(
                author = "",
                content = "",
                description = "",
                publishedAt = "2 hours",
                source = Source(id = "", name = "BBC"),
                title = "Her traint broke down. Her phone died. And then she met her saver in a",
                url = "",
                urlToImage = ""
            )
        ) {

        }
    }
}












