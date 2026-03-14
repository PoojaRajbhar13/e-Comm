package com.example.myecomartapp.presentation.screen.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myecomartapp.R
import com.example.myecomartapp.presentation.navigation.Route
import com.example.myecomartapp.presentation.viewmodel.UserPreferenceViewModel
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType
import kotlinx.coroutines.launch


@Composable
fun OnboardingScreen(
    userPreferenceViewModel: UserPreferenceViewModel,
    navHostController: NavHostController
) {
    val pagerState = rememberPagerState(
        pageCount = { OnbaordingDataSource.images.size }
    )
    val scope = rememberCoroutineScope()

    val onFinished = {
        userPreferenceViewModel.onBoardingFinished()
        navHostController.navigate(Route.Login) {
            popUpTo(Route.Onboarding) { inclusive = true }
        }
    }

    Scaffold(containerColor = Color.White) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //TopBar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${pagerState.currentPage + 1}/${pagerState.pageCount}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = { onFinished() }) {
                    Text(
                        text = "Skip",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.black)
                    )
                }
            }

            //Pager
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(OnbaordingDataSource.images[page]),
                        contentDescription = null,
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        text = stringResource(OnbaordingDataSource.headings[page]),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(OnbaordingDataSource.description),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 24.dp),
                        color = Color.Gray
                    )
                }
            }


            // DOTS + Button
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                DotsIndicator(
                    dotCount = pagerState.pageCount,
                    pagerState = pagerState,
                    type = ShiftIndicatorType(
                        dotsGraphic = DotGraphic(
                            size = 8.dp,
                            color = colorResource(R.color.black)
                        )
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    //prev
                    if (pagerState.currentPage > 0) {
                        TextButton(onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }) {
                            Text("Prev", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        TextButton(onClick = {}, enabled = false) {
                            Text("Prev", color = Color.LightGray)
                        }
                    }

                    //NEXT  / GET STARTED BUTTON
                    TextButton(onClick = {
                        scope.launch {
                            if (pagerState.currentPage == pagerState.pageCount - 1) {
                                onFinished()
                            } else {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    }) {
                        val buttonText =
                            if (pagerState.currentPage == pagerState.pageCount - 1) {
                                "Get Started"
                            } else {
                                "Next"
                            }

                        Text(
                            text = buttonText,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Blue
                        )
                    }
                }
            }
        }
    }
}
