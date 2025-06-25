package com.active.teethtime

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.active.teethtime.ui.theme.AppTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.lang.reflect.Type

class CalendarActivity : ComponentActivity() {
    private val gson = Gson()
    private val fileName = "session_data.json"

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold (
                    topBar = {
                        LargeTopAppBar(
                            title = { Text("Twoja passa") },
                            colors = TopAppBarDefaults.largeTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.back),
                                        contentDescription = "Back to main page"
                                    )
                                }
                            }
                        )

                    },

                ){
                    val daysCount = remember { mutableStateOf(0) }
                    val coroutineScope = rememberCoroutineScope()

                    LaunchedEffect(Unit) {
                        coroutineScope.launch(Dispatchers.IO) {
                            val loadedDaysCount = loadDaysCount()
                            daysCount.value = loadedDaysCount
                        }
                    }

                    Calendar(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),
                        daysCount = daysCount.value
                    )
                }

            }
        }
    }

    private fun loadDaysCount(): Int {
        val file = File(filesDir, fileName)
        return if (file.exists()) {
            val type: Type = object : TypeToken<SessionData>() {}.type
            val sessionData: SessionData = gson.fromJson(file.readText(), type)
            sessionData.daysCount
        } else {
            0
        }
    }
}

@Composable
fun Calendar(modifier: Modifier = Modifier, daysCount: Int) {
    val context = LocalContext.current
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = daysCount.toString(), fontSize = 100.sp, style = MaterialTheme.typography.displayLarge)
            Text(text = "dni", fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarPreview() {
    AppTheme {
        Calendar(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center),
            daysCount = 45
        )
    }
}
