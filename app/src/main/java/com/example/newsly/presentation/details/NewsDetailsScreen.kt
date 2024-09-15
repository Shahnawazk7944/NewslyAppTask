package com.example.newsly.presentation.details

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsly.domain.model.News
import com.example.newsly.domain.model.Source
import com.example.newsly.presentation.common.NewsDetailsButton
import com.example.newsly.presentation.details.components.DetailsTopBar
import com.example.newsly.presentation.nvgraph.Route.DetailsScreen
import com.example.newsly.ui.theme.NewslyTheme
import com.example.newsly.ui.theme.spacing
import com.example.newsly.R
import com.loc.newsapp.presentation.Dimens.ArticleImageHeight


@SuppressLint("QueryPermissionsNeeded")
@Composable
fun NewsDetailsScreen(
    news: News,
    event: (NewsDetailsEvent) -> Unit,
    navigateUp: () -> Unit,
    isBookmarked: Boolean
) {
    LaunchedEffect(key1 = true) {
        event(NewsDetailsEvent.IsBookmarked(news))
    }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DetailsTopBar(
            onBookmarkClick = { event(NewsDetailsEvent.BookmarkOrDeleteNews(news)) },
            onBackClick = navigateUp,
            isBookmarked = isBookmarked
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = MaterialTheme.spacing.extraLarge,
                end = MaterialTheme.spacing.extraLarge,
                top = MaterialTheme.spacing.extraLarge
            )
        ) {
            item {

                AsyncImage(
                    model = ImageRequest.Builder(context = context).data(news.urlToImage).build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ArticleImageHeight)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraLarge))

                Text(
                    text = news.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                Text(
                    text = news.content,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.secondary,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraLarge))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                    NewsDetailsButton(painterResource(R.drawable.share), content ="Share"){
                        Intent(Intent.ACTION_SEND).also {
                            it.putExtra(Intent.EXTRA_TEXT, news.url)
                            it.type = "text/plain"
                            if (it.resolveActivity(context.packageManager) != null) {
                                context.startActivity(it)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
                    NewsDetailsButton(painterResource(R.drawable.network), content = "Read More"){
                        Intent(Intent.ACTION_VIEW).also {
                            it.data = Uri.parse(news.url)
                            if (it.resolveActivity(context.packageManager) != null) {
                                context.startActivity(it)
                            }
                        }
                    }
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    NewslyTheme() {
        NewsDetailsScreen(
            news = News(
                author = "",
                title = "Coinbase says Apple blocked its last app release on NFTs in Wallet ... - CryptoSaurus",
                description = "Coinbase says Apple blocked its last app release on NFTs in Wallet ... - CryptoSaurus",
                content = "We use cookies and data to Deliver and maintain Google services Track outages and protect against spam, fraud, and abuse Measure audience engagement and site statistics to unde… [+1131 chars]",
                publishedAt = "2023-06-16T22:24:33Z",
                source = Source(
                    id = "", name = "bbc"
                ),
                url = "https://consent.google.com/ml?continue=https://news.google.com/rss/articles/CBMiaWh0dHBzOi8vY3J5cHRvc2F1cnVzLnRlY2gvY29pbmJhc2Utc2F5cy1hcHBsZS1ibG9ja2VkLWl0cy1sYXN0LWFwcC1yZWxlYXNlLW9uLW5mdHMtaW4td2FsbGV0LXJldXRlcnMtY29tL9IBAA?oc%3D5&gl=FR&hl=en-US&cm=2&pc=n&src=1",
                urlToImage = "https://media.wired.com/photos/6495d5e893ba5cd8bbdc95af/191:100/w_1280,c_limit/The-EU-Rules-Phone-Batteries-Must-Be-Replaceable-Gear-2BE6PRN.jpg"
            ),
            event = {},
            isBookmarked = true,
            navigateUp = {}
        )
    }
}






