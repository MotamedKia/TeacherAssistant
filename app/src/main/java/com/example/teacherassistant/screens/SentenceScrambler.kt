package com.example.teacherassistant.screens

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.teacherassistant.Logics.scrambledSentence
import com.example.teacherassistant.Logics.scrambledWord
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun SentenceScrambler(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    val label by remember { mutableStateOf("Write a sentence.") }
    var sentences by remember { mutableStateOf(listOf<String>()) }
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
            label = { Text(if (!onGenClick) scrambledSentence(label) else label) }
        )
        LazyColumn(modifier = modifier.padding(bottom = 43.dp)) {
            items(sentences) { sentence ->
                val displayedText = if (onChangeClick) {
                    scrambledSentence(sentence)
                } else {
                    scrambledSentence(sentence)
                }
                Row(
                    modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, end = 20.dp, start = 28.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = displayedText,
                        onValueChange = {
                            Toast.makeText(
                                context,
                                "You can only select this part",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = modifier.weight(1f), readOnly = true
                    )
                    Spacer(modifier.width(12.dp))
                    Button(onClick = {
                        clipboardManager.setText(annotatedString = AnnotatedString(displayedText))
                    }) { Text("copy") }
                }
            }
        }
    }
    Row(
        modifier.fillMaxSize(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        if (onGenClick) {
            OutlinedButton(
                onClick = { onChangeClick = !onChangeClick },
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) { Text("Change") }
        } else {
            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        sentences = text.split("\n").filter { it.isNotBlank() }
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