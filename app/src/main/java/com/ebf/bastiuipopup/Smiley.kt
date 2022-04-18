package com.ebf.bastiuipopup

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ebf.bastiuipopup.ui.theme.BastiUIPopupTheme

enum class Emotion {
    HAPPY_MAX,
    HAPPY,
    NEUTRAL,
    NOT_HAPPY,
    NOT_HAPPY_MAX
}

@Composable
fun SmileyRow(selected: Emotion?, onEmotionSelected: (Emotion?) -> Unit) {
    val onClick = { emotion: Emotion ->
        if (emotion == selected) {
            onEmotionSelected(null)
        } else {
            onEmotionSelected(emotion)
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Smiley(
            emotion = Emotion.NOT_HAPPY_MAX,
            onClick = onClick,
            disable = selected != null && selected != Emotion.NOT_HAPPY_MAX,
            selected = selected == Emotion.NOT_HAPPY_MAX
        )
        Smiley(
            emotion = Emotion.NOT_HAPPY,
            onClick = onClick,
            disable = selected != null && selected != Emotion.NOT_HAPPY,
            selected = selected == Emotion.NOT_HAPPY
        )
        Smiley(
            emotion = Emotion.NEUTRAL,
            onClick = onClick,
            disable = selected != null && selected != Emotion.NEUTRAL,
            selected = selected == Emotion.NEUTRAL
        )
        Smiley(
            emotion = Emotion.HAPPY,
            onClick = onClick,
            disable = selected != null && selected != Emotion.HAPPY,
            selected = selected == Emotion.HAPPY
        )
        Smiley(
            emotion = Emotion.HAPPY_MAX,
            onClick = onClick,
            disable = selected != null && selected != Emotion.HAPPY_MAX,
            selected = selected == Emotion.HAPPY_MAX
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Smiley(
    emotion: Emotion,
    selected: Boolean = false,
    disable: Boolean = false,
    onClick: (Emotion) -> Unit = {}
) {
    val initialDelay = (0..2000).random()
    val transition = rememberInfiniteTransition()
    val blinkLeft by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 100, delayMillis = 2000),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(offsetMillis = initialDelay)
        )
    )
    val blinkRight by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 100, delayMillis = 2000),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(offsetMillis = initialDelay + 40)
        )
    )

    val offsetX by animateFloatAsState(
        targetValue = when (selected) {
            true -> with(LocalDensity.current) { 0.dp.toPx() }
            false -> with(LocalDensity.current) { 4.dp.toPx() }
        }
    )
    val surfaceAlpha by animateFloatAsState(
        targetValue = when (selected) {
            true -> 1f
            false -> 0f
        }
    )
    val canvasPadding by animateDpAsState(
        targetValue = when (selected) {
            true -> 8.dp
            false -> 0.dp
        }
    )
    val disableAlpha by animateFloatAsState(
        targetValue = when (disable) {
            true -> 0.5f
            false -> 1f
        }
    )

    Surface(
        color = content1.copy(alpha = surfaceAlpha),
        shape = CircleShape,
        onClick = { onClick(emotion) },
        modifier = Modifier.padding(vertical = 8.dp - canvasPadding)
    ) {
        Canvas(
            modifier = Modifier
                .padding(all = canvasPadding)
                .size(56.dp)
                .alpha(disableAlpha)
        ) {
            when (emotion) {
                Emotion.HAPPY_MAX -> faceHappyMax(
                    blinkLeft = blinkLeft,
                    blinkRight = blinkRight,
                    offsetX = offsetX
                )
                Emotion.HAPPY -> faceHappy(
                    blinkLeft = blinkLeft,
                    blinkRight = blinkRight,
                    offsetX = offsetX
                )
                Emotion.NEUTRAL -> faceNeutral(
                    blinkLeft = blinkLeft,
                    blinkRight = blinkRight,
                    offsetX = offsetX
                )
                Emotion.NOT_HAPPY -> faceNotHappy(
                    blinkLeft = blinkLeft,
                    blinkRight = blinkRight,
                    offsetX = offsetX
                )
                Emotion.NOT_HAPPY_MAX -> faceNotHappyMax(
                    blinkLeft = blinkLeft,
                    blinkRight = blinkRight,
                    offsetX = offsetX
                )
            }
        }
    }

}

fun DrawScope.faceHappyMax(blinkLeft: Float, blinkRight: Float, offsetX: Float) {
    drawCircle(
        brush = Brush.linearGradient(
            colors = listOf(Color(0xffffe630), Color(0xfffff36a)),
            start = Offset(x = 0f, y = size.height),
            end = Offset(x = size.width, y = 0f)
        ),
        radius = 28.dp.toPx()
    )
    translate(left = offsetX) {
        eyes(
            x = 8.dp.toPx(),
            height = 12.dp.toPx(),
            blinkLeft = blinkLeft,
            blinkRight = blinkRight
        )
        drawArc(
            color = bg1,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = false,
            size = Size(width = 16.dp.toPx(), height = 16.dp.toPx()),
            topLeft = Offset(x = 20.dp.toPx(), y = 12.dp.toPx())
        )
    }
}

fun DrawScope.faceHappy(blinkLeft: Float, blinkRight: Float, offsetX: Float) {
    drawCircle(
        brush = Brush.linearGradient(
            colors = listOf(Color(0xffffc427), Color(0xffffe455)),
            start = Offset(x = 0f, y = size.height),
            end = Offset(x = size.width, y = 0f)
        ),
        radius = 28.dp.toPx()
    )
    translate(left = offsetX) {
        eyes(x = 8.dp.toPx(), height = 18.dp.toPx(), blinkLeft = blinkLeft, blinkRight = blinkRight)
        drawArc(
            color = bg1,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = false,
            style = Stroke(width = 4.dp.toPx()),
            size = Size(width = 12.dp.toPx(), height = 12.dp.toPx()),
            topLeft = Offset(x = 22.dp.toPx(), y = 20.dp.toPx())
        )
    }
}

fun DrawScope.faceNeutral(blinkLeft: Float, blinkRight: Float, offsetX: Float) {
    drawCircle(
        brush = Brush.linearGradient(
            colors = listOf(Color(0xffffaa3f), Color(0xffffd784)),
            start = Offset(x = 0f, y = size.height),
            end = Offset(x = size.width, y = 0f)
        ),
        radius = 28.dp.toPx()
    )
    translate(left = offsetX) {
        eyes(x = 8.dp.toPx(), height = 24.dp.toPx(), blinkLeft = blinkLeft, blinkRight = blinkRight)
        drawRect(
            color = bg1,
            topLeft = Offset(x = 20.dp.toPx(), y = 32.dp.toPx()),
            size = Size(width = 16.dp.toPx(), height = 4.dp.toPx()),
        )
    }
}

fun DrawScope.faceNotHappy(blinkLeft: Float, blinkRight: Float, offsetX: Float) {
    drawCircle(
        brush = Brush.linearGradient(
            colors = listOf(Color(0xffff5627), Color(0xffffb054)),
            start = Offset(x = 0f, y = size.height),
            end = Offset(x = size.width, y = 0f)
        ),
        radius = 28.dp.toPx()
    )
    translate(left = offsetX) {
        eyes(x = 8.dp.toPx(), height = 28.dp.toPx(), blinkLeft = blinkLeft, blinkRight = blinkRight)
        drawArc(
            color = bg1,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            style = Stroke(width = 4.dp.toPx()),
            size = Size(width = 12.dp.toPx(), height = 12.dp.toPx()),
            topLeft = Offset(x = 22.dp.toPx(), y = 34.dp.toPx())
        )
    }
}

fun DrawScope.faceNotHappyMax(blinkLeft: Float, blinkRight: Float, offsetX: Float) {
    drawCircle(
        brush = Brush.linearGradient(
            colors = listOf(Color(0xffff3c27), Color(0xffff8354)),
            start = Offset(x = 0f, y = size.height),
            end = Offset(x = size.width, y = 0f)
        ),
        radius = 28.dp.toPx()
    )
    translate(left = offsetX) {
        eyes(x = 8.dp.toPx(), height = 34.dp.toPx(), blinkLeft = blinkLeft, blinkRight = blinkRight)
        drawArc(
            color = bg1,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            style = Stroke(width = 4.dp.toPx()),
            size = Size(width = 12.dp.toPx(), height = 12.dp.toPx()),
            topLeft = Offset(x = 22.dp.toPx(), y = 40.dp.toPx())
        )
        rotate(12f) {
            drawRect(
                color = bg1,
                topLeft = Offset(x = 7.dp.toPx(), y = 31.dp.toPx()),
                size = Size(width = 12.dp.toPx(), height = 4.dp.toPx()),
            )
        }
        rotate(-12f) {
            drawRect(
                color = bg1,
                topLeft = Offset(x = 37.dp.toPx(), y = 31.dp.toPx()),
                size = Size(width = 12.dp.toPx(), height = 4.dp.toPx()),
            )
        }
    }
}

fun DrawScope.eyes(x: Float, height: Float, blinkLeft: Float, blinkRight: Float) {
    eye(position = Offset(x = x, y = height), blink = blinkLeft)
    eye(position = Offset(x = x + 32.dp.toPx(), y = height), blink = blinkRight)
}

fun DrawScope.eye(position: Offset, blink: Float) {
    drawArc(
        color = bg1,
        topLeft = position.copy(y = position.y + 4.dp.toPx() * (1 - blink)),
        size = Size(width = 8.dp.toPx(), height = 8.dp.toPx() * blink),
        startAngle = 0f,
        sweepAngle = 360f,
        useCenter = true
    )
}

@Preview
@Composable
fun SmileyPreview() {
    BastiUIPopupTheme {
        SmileyRow(selected = Emotion.HAPPY, onEmotionSelected = {})
    }
}
