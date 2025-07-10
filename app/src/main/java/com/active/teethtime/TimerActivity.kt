package com.active.teethtime

import android.annotation.SuppressLint
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import androidx.compose.foundation.Image
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.active.teethtime.ui.theme.AppTheme
import kotlinx.coroutines.delay

class TimerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                ToothbrushTimerScreen(
                    onDestroy = {
                        finish()
                    },
                    onTimerFinished = {
                        val intent = Intent(this, FinishActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ToothbrushTimerScreen(modifier: Modifier = Modifier, onTimerFinished: () -> Unit, onDestroy: () -> Unit) {
    var series by remember { mutableStateOf(0) }
    var timeLeft by remember { mutableStateOf(20) }
    var timerRunning by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }

    val seriesImages = listOf(
        R.drawable.t,
        R.drawable.t2,
        R.drawable.t3,
        R.drawable.t4,
        R.drawable.t5,
        R.drawable.t6
    )
    val context = LocalContext.current
    LaunchedEffect(series, timerRunning) {

        val vibrator = context.getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (timerRunning && series < 6) {
            for (second in 20 downTo 1) {
                delay(1000L)
                timeLeft = second
            }
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    500L,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
            series++
            timeLeft = 20
            if (series == 6) {
                onTimerFinished()
            }
        }
    }

        Surface(color = MaterialTheme.colorScheme.primaryContainer) {
            ProgrCircle(tl = timeLeft)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = seriesImages[series.coerceAtMost(5)]),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                )

                Spacer(modifier = Modifier.height(55.dp))

                Text(
                    text = "$timeLeft",
                    fontSize = 48.sp,
                )
            }
            Row(modifier = Modifier
                .padding(32.dp)
                .fillMaxSize()
                .fillMaxWidth()
                .fillMaxHeight(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Absolute.Left)
            {
                FloatingActionButton(onClick = { showDialog = true } ) {
                    Icon(painter = painterResource(R.drawable.back), contentDescription = "Cancel")
                }
            }
        }


        if (showDialog) {
            val context = LocalContext.current
            val intent = Intent(context, MainActivity::class.java)
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Anulowanie sesji") },
                text = { Text("Czy na pewno chcesz anulować sesję?") },
                confirmButton = {
                    TextButton(onClick = { onDestroy() }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("No")
                    }
                }
            )
        }
    }

@Composable
fun ProgrCircle(tl: Int, modifier: Modifier = Modifier){
    Column (
        modifier = modifier
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        CircularProgressIndicator(
            progress = (20 - tl) / 20f ,
            modifier = Modifier
                .size(300.dp)
                .padding(16.dp)
                .clip(CircleShape),
            strokeWidth = 5.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimerPreview() {
    AppTheme {
        ToothbrushTimerScreen(
            onDestroy = {
                /*TODO*/
            },
            onTimerFinished = {
                /*TODO*/
            }
        )
    }
}
