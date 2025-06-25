package com.active.teethtime

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHost
import com.active.teethtime.ui.theme.AppTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                HomeScreenPreview()
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier){
    val context = LocalContext.current
    Surface (color = MaterialTheme.colorScheme.primaryContainer){
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logotype"
            )
            val intent = Intent(context, CountdownActivity::class.java)
            Spacer(modifier = Modifier.height(80.dp))
            Button(onClick = { context.startActivity(intent) }) {
                Text(stringResource(R.string.start))
            }
        }
        NavBar()
    }

}

@Composable
fun NavBar(modifier: Modifier = Modifier){
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .padding(bottom = 30.dp)
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Absolute.Center


    ){
        val intent = Intent(context, CalendarActivity::class.java)
        val calendarButton = FloatingActionButton(onClick = { context.startActivity(intent) }, containerColor = BottomAppBarDefaults.bottomAppBarFabColor) {
            Icon(painter = painterResource(R.drawable.calendar), contentDescription = "Calendar")
        }
        Spacer(modifier = Modifier.width(50.dp))
        val otherIntent = Intent(context, HelpActivity::class.java)
        FloatingActionButton(onClick = { context.startActivity(otherIntent) }, containerColor = BottomAppBarDefaults.bottomAppBarFabColor) {
            Icon(painter = painterResource(R.drawable.help), contentDescription = "Help")
        }


    }
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)
    )
}