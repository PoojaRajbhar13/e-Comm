package com.example.myecomartapp.presentation.common

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp



private const val PADDING_PERCENTAGE_OUTER_CIRCLE = 0.15f //15%
private const val PADDING_PERCENTAGE_INNER_CIRCLE = 0.3f  //30%
private const val POSITION_START_OFFSET_OUTER_CIRCLE = 90f  //90
private const val POSITION_START_OFFSET_INNER_CIRCLE = 135f //135

@Composable
fun LoadingIndicator(){

    val infiniteTransition = rememberInfiniteTransition(/*label = "infinite transition"*/)
    val rotation = infiniteTransition.animateFloat(
        initialValue = 0f, //starting value
        targetValue = 360f, //end value
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000), // 1 sec me ek round
        ),
        label = "rotation animation"
    )
    var width by remember { mutableIntStateOf(0) }
Box(
    modifier  = Modifier
        .size(40.dp)
        .onSizeChanged{
            width = it.width
        },
    contentAlignment = Alignment.Center
){

    CircularProgressIndicator(
        color = Color.Blue,
        strokeWidth = 1.dp,
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer{
                rotationZ = rotation.value
                }
    )
    CircularProgressIndicator(
        color = Color.Blue,
        strokeWidth = 1.dp,  // line ki motai
        modifier = Modifier
            .fillMaxSize()
            .padding(
                with(LocalDensity.current){
                    ( width * PADDING_PERCENTAGE_INNER_CIRCLE).toDp()
                }
            )
            .graphicsLayer{
                rotationZ = rotation.value + POSITION_START_OFFSET_INNER_CIRCLE
            }
    )
    CircularProgressIndicator(
        color = Color.Blue,
        strokeWidth = 1.dp,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                with(LocalDensity.current){
                    ( width * PADDING_PERCENTAGE_OUTER_CIRCLE).toDp()
                }
            )
            .graphicsLayer{
                rotationZ = rotation.value + POSITION_START_OFFSET_OUTER_CIRCLE
            }
    )

}

}





@Preview(
   showBackground = true
)
@Composable
 fun LoadingIndicatorPreview(){
        LoadingIndicator()
    }
