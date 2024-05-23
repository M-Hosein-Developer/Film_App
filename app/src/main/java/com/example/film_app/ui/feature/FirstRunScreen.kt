package com.example.film_app.ui.feature

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.film_app.R
import com.example.film_app.util.BottomNavItem
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun FirstRunScreen(navController: NavHostController) {

    Column(
        Modifier
            .fillMaxSize()
    ) {


        val sliderList = remember {
            mutableListOf(
                R.drawable.inttro1,
                R.drawable.intro2,
                R.drawable.intro3,
            )
        }

        val sliderTextList = remember {
            mutableListOf(
                "Watch the latest and trendiest movies with your family",
                "Get together with your friends and watch a movie",
                "You can see the trailer of the movie you want to choose",
            )
        }


        CustomSlider(sliderList = sliderList , sliderTextList = sliderTextList)

        Button(
            onClick = { navController.navigate(BottomNavItem.SignInScreen.rout) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
                .padding(horizontal = 32.dp),
        ) {
            Text(
                text = "Lets GO",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
        }


    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomSlider(
    modifier: Modifier = Modifier,
    sliderList: MutableList<Int>,
    sliderTextList: MutableList<String>,
    pagerPaddingValues: PaddingValues = PaddingValues(horizontal = 65.dp),
    imageCornerRadius: Dp = 16.dp,
    imageHeight: Dp = 250.dp,
    dotsSize: Dp = 10.dp,
    dotsActiveColor: Color = Color.DarkGray,
    dotsInActiveColor: Color = Color.LightGray,
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        sliderList.size
    }
    val scope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
    ) {

        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {

            HorizontalPager(
                modifier = modifier.weight(1f),
                state = pagerState,
                pageSpacing = 0.dp,
                userScrollEnabled = true,
                reverseLayout = false,
                contentPadding = pagerPaddingValues,
                beyondBoundsPageCount = 0,
                pageSize = PageSize.Fill,
                pageContent = {

                    val pageOffset =
                        (pagerState.currentPage - it) + pagerState.currentPageOffsetFraction

                    val scaleFactor = 0.75f + (1f - 0.75f) * (1f - pageOffset.absoluteValue)


                    Box(modifier = modifier
                        .graphicsLayer {
                            scaleX = scaleFactor
                            scaleY = scaleFactor
                        }
                        .alpha(
                            scaleFactor.coerceIn(0f, 1f)
                        )
                        .padding(10.dp)
                        .clip(RoundedCornerShape(imageCornerRadius))) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            AsyncImage(
                                model = sliderList[it],
                                contentDescription = "Image",
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(id = R.drawable.empty),
                                modifier = modifier.height(imageHeight)
                            )

                            Text(
                                text = sliderTextList[it],
                                modifier = Modifier.padding(top = 24.dp),
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Serif
                                ),
                                textAlign = TextAlign.Center
                            )

                        }
                    }

                }
            )

        }


        Row(
            modifier
                .height(50.dp)
                .padding(top = 20.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            repeat(sliderList.size) {
                val color = if (pagerState.currentPage == it) dotsActiveColor else dotsInActiveColor
                Box(modifier = modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .size(dotsSize)
                    .background(color)
                    .clickable {
                        scope.launch {
                            pagerState.animateScrollToPage(it)
                        }
                    })
            }
        }


    }

}


