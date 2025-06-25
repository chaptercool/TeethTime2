package com.active.teethtime

import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.active.teethtime.ui.theme.AppTheme
import kotlinx.coroutines.delay

class CountdownActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                CountdownTimer(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    initialTimeMillis = 3000L,
                    onTimerFinished = {
                        val intent = Intent(this, TimerActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}


@Composable
fun CountdownTimer(
    initialTimeMillis: Long,
    onTimerFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface (color = MaterialTheme.colorScheme.primaryContainer){
        Column (
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            var remainingTimeMillis by remember { mutableStateOf(initialTimeMillis) }
            val context = LocalContext.current
            val vibrator = context.getSystemService(VIBRATOR_SERVICE) as Vibrator

            LaunchedEffect(remainingTimeMillis) {
                if (remainingTimeMillis > 0) {
                    delay(1000L)
                    remainingTimeMillis -= 1000L
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            500L,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    onTimerFinished()
                }
            }

            Text(
                text = "${remainingTimeMillis / 1000}",
                style = MaterialTheme.typography.displayLarge,
                fontSize = 200.sp
            )
        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun CountdownPreview() {
//    val context = LocalContext.current
//    val intent = Intent(context, TimerActivity::class.java)
//    CountdownTimer(
//        modifier = Modifier
//            .fillMaxSize()
//            .wrapContentSize(Alignment.Center),
//        initialTimeMillis = 3000L,
//        onTimerFinished = { context.startActivity(intent) }
//    )
//}