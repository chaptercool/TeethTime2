package com.active.teethtime

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.active.teethtime.ui.theme.AppTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class SessionData(
    var timestamp: String,
    var daysCount: Int
)

class FinishActivity : ComponentActivity() {
    private val gson = Gson()
    private val fileName = "session_data.json"
    private lateinit var sessionData: SessionData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                FinishPreview()
            }
        }

        loadOrCreateSessionData()
        updateSessionData()

        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 5000)
    }

    private fun loadOrCreateSessionData() {
        val file = File(filesDir, fileName)
        if (file.exists()) {
            val type: Type = object : TypeToken<SessionData>() {}.type
            sessionData = gson.fromJson(file.readText(), type)
        } else {
            sessionData = SessionData(getCurrentDate(), 1)
            saveSessionData()
        }
    }

    private fun updateSessionData() {
        val currentDate = getCurrentDate()
        if (sessionData.timestamp != currentDate) {
            sessionData.timestamp = currentDate
            sessionData.daysCount += 1
            saveSessionData()
        }
    }

    private fun saveSessionData() {
        val file = File(filesDir, fileName)
        file.writeText(gson.toJson(sessionData))
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }
}

@Composable
fun FinishScreen(modifier: Modifier = Modifier){
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        Column (
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = "Dobra robota!",
                style = MaterialTheme.typography.displayLarge,
                fontSize = 50.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Nie zapomnij o spłukaniu!",
                fontSize = 17.sp,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Gray
            )
        }
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ){
            LinearProgressIndicator(
                modifier = Modifier.width(150.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Czekaj, trwa zapis danych...",
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FinishPreview() {
    AppTheme {
        FinishScreen(modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center))
    }
}
