package com.example.teacherassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.TeacherAssistantTheme
import com.example.teacherassistant.screens.NavGraphs
import com.example.teacherassistant.screens.destinations.FillGapsDestination
import com.example.teacherassistant.screens.destinations.HomeScreenDestination
import com.example.teacherassistant.screens.destinations.SentenceScramblerDestination
import com.example.teacherassistant.screens.destinations.WordScramblerDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TeacherAssistantTheme {
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val navController = rememberNavController()
                val activity = LocalActivity.current
                val currentBackStackentry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackentry?.destination?.route
                val title = when (currentRoute) {
                    FillGapsDestination.route -> "Fill Gaps"
                    SentenceScramblerDestination.route -> "Sentence Scrambler"
                    WordScramblerDestination.route -> "Word Scrambler"
                    else -> "The Teacher Assistant"
                }

                ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
                    ModalDrawerSheet {
                        Spacer(Modifier.height(24.dp))
                        Text(
                            "Word Scrambler",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp)
                                .clickable {
                                    navController.navigate(WordScramblerDestination.route)
                                    scope.launch { drawerState.close() }
                                }
                                .padding(vertical = 12.dp)
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.primary)
                        Text("Sentence Scrambler",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp)
                                .clickable {
                                    navController.navigate(SentenceScramblerDestination)
                                    scope.launch { drawerState.close() }
                                }
                                .padding(vertical = 12.dp)
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.primary)
                        Text("Fill Gaps",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp)
                                .clickable {
                                    navController.navigate(FillGapsDestination)
                                    scope.launch { drawerState.close() }
                                }
                                .padding(vertical = 12.dp)
                        )
                        HorizontalDivider(color = MaterialTheme.colorScheme.primary)
                    }
                }) {
                    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text(title, color = MaterialTheme.colorScheme.primary) },
                            navigationIcon = {
                                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                    Icon(
                                        Icons.Filled.Menu,
                                        "Menu",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            })
                    }) { innerPadding ->
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            modifier = Modifier.padding(innerPadding),
                            navController = navController
                        )
                        BackHandler(enabled = true) {
                            if (currentRoute != HomeScreenDestination.route) {
                                navController.navigate(HomeScreenDestination.route)
                            } else {
                                activity?.finish()
                            }
                        }
                    }
                }
            }
        }
    }
}