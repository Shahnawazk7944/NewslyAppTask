package com.example.newsly.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsly.ui.theme.NewslyTheme
import com.example.newsly.ui.theme.spacing
import com.loc.newsapp.presentation.Dimens


fun Modifier.shimmerEffect() = composed {
    val transition = rememberInfiniteTransition(label = "")
    val alpha = transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    ).value
   // background(color = Color.LightGray.copy(alpha = alpha))
    background(color = MaterialTheme.colorScheme.tertiary.copy(alpha = alpha))
}


@Composable
fun ArticleCardShimmerEffect(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(270.dp)
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(vertical = MaterialTheme.spacing.extraSmall),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.ArticleCardSize)
                    .clip(MaterialTheme.shapes.medium)
                    .shimmerEffect()
            )
            Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .padding(horizontal = MaterialTheme.spacing.extraSmall)
                    .clip(MaterialTheme.shapes.extraSmall)
                    .shimmerEffect()
            )
            Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
            Box(
                modifier = Modifier
                    .fillMaxWidth(.7f)
                    .height(20.dp)
                    .padding(horizontal = MaterialTheme.spacing.extraSmall)
                    .clip(MaterialTheme.shapes.extraSmall)
                    .shimmerEffect()
            )

            Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                //horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.extraSmall)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(MaterialTheme.shapes.extraSmall)
                        .height(20.dp)
                        .shimmerEffect()
                )
                Spacer(Modifier.width(MaterialTheme.spacing.extraSmall))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(MaterialTheme.shapes.extraSmall)
                        .height(20.dp)
                        .shimmerEffect()
                )
            }
        }

        Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = MaterialTheme.spacing.extraSmall)
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.ArticleCardSize)
                    .clip(MaterialTheme.shapes.medium)
                    .shimmerEffect()
            )
            Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .padding(horizontal = MaterialTheme.spacing.extraSmall)
                    .clip(MaterialTheme.shapes.extraSmall)
                    .shimmerEffect()
            )
            Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
            Box(
                modifier = Modifier
                    .fillMaxWidth(.7f)
                    .height(20.dp)
                    .padding(horizontal = MaterialTheme.spacing.extraSmall)
                    .clip(MaterialTheme.shapes.extraSmall)
                    .shimmerEffect()
            )

            Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraSmall)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .weight(1f)
                        .height(20.dp)
                        .clip(MaterialTheme.shapes.extraSmall)
                        .shimmerEffect()
                )
                Spacer(Modifier.width(MaterialTheme.spacing.extraSmall))
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraSmall)
                        .weight(1f)
                        .height(20.dp)

                        .shimmerEffect()
                )
            }
        }


    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardShimmerEffectPreview() {
    NewslyTheme {
        ArticleCardShimmerEffect()
    }
}