package com.jetpack.customtablayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.jetpack.customtablayout.ui.theme.CustomTabLayoutTheme
import com.jetpack.customtablayout.ui.theme.Purple200
import com.jetpack.customtablayout.ui.theme.Purple500
import com.jetpack.customtablayout.ui.theme.Purple700
import kotlinx.coroutines.launch

@ExperimentalPagerApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomTabLayoutTheme {
                Surface(color = MaterialTheme.colors.background) {
                    CustomTabLayout()
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun CustomTabLayout() {
    val tabItems = listOf("Tab1", "Tab2", "Tab3")
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = Purple500,
            modifier = Modifier
                .padding(5.dp)
                .background(Color.Transparent)
                .clip(RoundedCornerShape(30.dp)),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .pagerTabIndicatorOffset(
                            pagerState, tabPositions
                        )
                        .width(0.dp)
                        .height(0.dp)
                )
            }
        ) {
            tabItems.forEachIndexed { index, title ->
                val color = remember { Animatable(Purple700) }
                LaunchedEffect(pagerState.currentPage == index) {
                    color.animateTo( if (pagerState.currentPage == index)
                        Color.White else Purple500)
                }

                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = title,
                            style = if (pagerState.currentPage == index) {
                                TextStyle(
                                    color = Purple700,
                                    fontSize = 18.sp
                                )
                            } else {
                                TextStyle(
                                    color = Purple700,
                                    fontSize = 16.sp
                                )
                            }
                        )
                    },
                    modifier = Modifier.background(
                        color = color.value,
                        shape = RoundedCornerShape(30.dp)
                    )
                )
            }
        }

        HorizontalPager(
            count = tabItems.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .background(Purple200)
        ) { page ->
            Text(
                text = tabItems[page],
                modifier = Modifier.padding(50.dp),
                color = Color.White
            )
        }
    }
}





















