package com.example.teacherassistant.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.teacherassistant.modals.informList
import com.example.teacherassistant.screens.destinations.FillGapsDestination
import com.example.teacherassistant.screens.destinations.SentenceScramblerDestination
import com.example.teacherassistant.screens.destinations.WordScramblerDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, navigator: DestinationsNavigator) {
    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(modifier.padding(horizontal = 12.dp, vertical = 12.dp)) {
            LazyColumn {
                items(informList) {
                    HomeScreenItem(modifier, it, onClick = {
                        when (it.id) {
                            1 -> navigator.navigate(WordScramblerDestination.route)
                            2 -> navigator.navigate(SentenceScramblerDestination.route)
                            3 -> navigator.navigate(FillGapsDestination.route)
                            else -> Toast.makeText(
                                context,
                                "not a valid request",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }
            }
        }
    }
}