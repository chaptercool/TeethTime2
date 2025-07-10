package com.active.teethtime

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home // Example home icon
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.active.teethtime.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                MainScreenWithBottomBar()
            }
        }
    }
}

object AppDestinations {
    const val HOME = "home"
    const val CALENDAR = "calendar"
    const val HELP = "help"
}

@Composable
fun MainScreenWithBottomBar() {
    val context = LocalContext.current
    var currentDestination by remember { mutableStateOf(AppDestinations.HOME) }

    Scaffold(
        bottomBar = {
            AppNavigationBar(
                currentDestination = currentDestination,
                onNavigate = { destination ->
                    currentDestination = destination
                    when (destination) {
                        AppDestinations.CALENDAR -> {
                            val intent = Intent(context, CalendarActivity::class.java)
                            context.startActivity(intent)
                        }
                        AppDestinations.HELP -> {
                            val intent = Intent(context, HelpActivity::class.java)
                            context.startActivity(intent)
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        HomeScreenContent(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun AppNavigationBar(currentDestination: String, onNavigate: (String) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = stringResource(R.string.home_tab)) },
            label = { Text(stringResource(R.string.home_tab)) },
            selected = currentDestination == AppDestinations.HOME,
            onClick = { onNavigate(AppDestinations.HOME) }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.calendar), contentDescription = stringResource(R.string.calendar_tab)) },
            label = { Text(stringResource(R.string.calendar_tab)) },
            selected = currentDestination == AppDestinations.CALENDAR,
            onClick = { onNavigate(AppDestinations.CALENDAR) }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.help), contentDescription = stringResource(R.string.help_tab)) },
            label = { Text(stringResource(R.string.help_tab)) },
            selected = currentDestination == AppDestinations.HELP,
            onClick = { onNavigate(AppDestinations.HELP) }
        )
    }
}

@Composable
fun HomeScreenContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier.wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logotype"
        )
        Spacer(modifier = Modifier.height(80.dp))
        Button(onClick = {
            val intent = Intent(context, CountdownActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(stringResource(R.string.start))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenWithBottomBarPreview() {
    AppTheme {
        MainScreenWithBottomBar()
    }
}
