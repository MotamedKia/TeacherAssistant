package com.example.teacherassistant.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.example.teacherassistant.Logics.scrambledSentence
import com.example.teacherassistant.Logics.scrambledWord
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun WordScrambler(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    val label by remember { mutableStateOf("Write a sentence.") }
    var input by remember { mutableStateOf("") }
    var scrambledInput by remember { mutableStateOf(scrambledWord(input)) }
    var onChangeClick by remember { mutableStateOf(false) }
    var onGenClick by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                onGenClick = false
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 20.dp),
            label = { Text(if (!onGenClick) scrambledWord(label) else label) }
        )
        if (onGenClick) {
            Spacer(modifier.height(24.dp))
            Row(
                modifier = modifier.padding(start = 28.dp, end = 20.dp, bottom = 43.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = if (onChangeClick) {
                        scrambledInput
                    } else {
                        scrambledInput
                    },
                    onValueChange = {
                        Toast.makeText(
                            context,
                            "You can only select this part",
                            Toast.LENGTH_SHORT
                        ).show()
                    }, readOnly = true, modifier = modifier.weight(1f)
                )
                Spacer(modifier.width(12.dp))
                Button(onClick = {
                    clipboardManager.setText(annotatedString = AnnotatedString(scrambledInput))
                }) { Text("copy") }
            }
        }
    }
    Row(
        modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        if (onGenClick) {
            OutlinedButton(
                onClick = {
                    onChangeClick = !onChangeClick
                    scrambledInput = scrambledWord(input)
                },
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) { Text("Change") }
        } else {
            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        onChangeClick = !onChangeClick
                        input = text
                        scrambledInput = scrambledWord(input)
                        text = "" // Clear the input after splitting
                        onGenClick = true
                    }
                },
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) { Text("Generate") }
        }
    }
}