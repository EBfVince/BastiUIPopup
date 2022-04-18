package com.ebf.bastiuipopup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ebf.bastiuipopup.ui.theme.BastiUIPopupTheme
import com.ebf.bastiuipopup.ui.theme.NeueMachina

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BastiUIPopupTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = bg1
                ) {
                    Composition()
                }
            }
        }
    }
}

val bg1 = Color(0xff00232B)
val bg3 = Color(0xff1D4D56)
val content1 = Color(0xffFFFFFF)

@Composable
fun Composition() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (card, card2, pattern, circle) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.pattern),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(300.dp)
                .rotate(-7f)
                .constrainAs(pattern) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        Card(
            modifier = Modifier.constrainAs(card2) {
                start.linkTo(card.start, margin = (-10).dp)
                end.linkTo(card.end, margin = (10).dp)
                top.linkTo(card.top, margin = 10.dp)
                bottom.linkTo(card.bottom, margin = (-10).dp)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            backgroundColor = bg1,
            shape = RectangleShape,
            border = BorderStroke(width = 4.dp, color = content1),
            content = {}
        )

        BastiCard(
            modifier = Modifier.constrainAs(card) {
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 8.dp)
                centerVerticallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )

        Circle(
            modifier = Modifier.constrainAs(circle) {
                end.linkTo(parent.end, margin = 20.dp)
                top.linkTo(card.top, margin = (-40).dp)
            }
        )
    }
}

@Composable
fun BastiCard(modifier: Modifier = Modifier) {
    var selected by remember { mutableStateOf<Emotion?>(null) }

    val buttonHeight by animateDpAsState(
        targetValue = when (selected != null) {
            true -> 100.dp
            false -> 0.dp
        }
    )

    val text = { emotion: Emotion? ->
        when (emotion) {
            Emotion.HAPPY_MAX -> "5/5 — Géniallll !"
            Emotion.HAPPY -> "4/5 — Au top !"
            Emotion.NEUTRAL -> "3/5 — M'ouais"
            Emotion.NOT_HAPPY -> "2/5 — Mheeh"
            Emotion.NOT_HAPPY_MAX -> "1/5 — Pignolesque"
            else -> ""
        }
    }

    Card(
        backgroundColor = bg1,
        shape = RectangleShape,
        border = BorderStroke(width = 4.dp, color = content1),
        modifier = modifier
    ) {
        Column {
            TextButton(
                onClick = { /*TODO*/ },
                Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Close, contentDescription = null, tint = content1)
                Text(
                    text = "Passer",
                    color = content1,
                    fontFamily = NeueMachina,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
            Column(Modifier.padding(32.dp)) {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = "Bravo Benjamin, tu viens de télécharger Bootstrap v5.1.3",
                    fontSize = 15.sp,
                    fontFamily = NeueMachina,
                    color = content1
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Dis-nous ce que tu en as pensé !",
                    fontSize = 34.sp,
                    fontFamily = NeueMachina,
                    fontWeight = FontWeight.ExtraBold,
                    color = content1
                )
                Spacer(modifier = Modifier.height(24.dp))
                SmileyRow(selected, onEmotionSelected = { selected = it })
                Spacer(modifier = Modifier.height(24.dp))
                AnimatedVisibility(visible = selected != null) {
                    Text(
                        text = text(selected),
                        fontSize = 15.sp,
                        fontFamily = NeueMachina,
                        color = content1
                    )
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = content1),
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(buttonHeight)
            ) {
                Text(
                    text = "Envoyer",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = NeueMachina,
                    color = bg1
                )
            }
        }

    }
}

@Composable
fun Circle(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition()
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 20000,
                easing = LinearEasing
            )
        )
    )

    Surface(
        color = bg3,
        shape = CircleShape,
        modifier = modifier.size(80.dp),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.love),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(rotation)
            )
            Image(
                painter = painterResource(id = R.drawable.coeur),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    BastiUIPopupTheme {
        BastiCard()
    }
}
