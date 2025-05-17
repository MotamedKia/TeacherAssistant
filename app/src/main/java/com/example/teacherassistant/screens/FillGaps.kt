package com.example.teacherassistant.screens

import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import com.example.teacherassistant.Logics.gapSentenceMaker
import com.example.teacherassistant.Logics.scrambledSentence
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun FillGaps(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    val label by remember { mutableStateOf("Write a sentence.") }
    var onGenClick by remember { mutableStateOf(false) }
    var sentences by remember { mutableStateOf(listOf<String>()) }
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
            label = { Text(if (!onGenClick) gapSentenceMaker(label)[2] else label) }
        )
        LazyColumn {
            items(sentences) { sentence ->
                Row(
                    modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, end = 20.dp, start = 28.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = sentence,
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
                        clipboardManager.setText(annotatedString = AnnotatedString(sentence))
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
        if (!onGenClick) {
            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        sentences = gapSentenceMaker(text)
                        text = "" // Clear the input after splitting
                        onGenClick = true
                    }
                },
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) { Text("Generate") }
        }
    }
}