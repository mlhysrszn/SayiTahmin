package com.mlhysrszn.sayitahmin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

const val MIN_VALUE = 1
const val MAX_VALUE = 101
const val ATTEMPT_COUNT = 10

@Composable
fun NumberGuessGame() {
    var targetNumber by remember { mutableIntStateOf(Random.nextInt(MIN_VALUE, MAX_VALUE)) }
    var remainingAttempts by remember { mutableIntStateOf(ATTEMPT_COUNT) }
    var userGuess by remember { mutableStateOf(TextFieldValue()) }
    var gameMessage by remember { mutableStateOf("") }
    var isGameStarted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isGameStarted) {
            Button(
                onClick = {
                    targetNumber = Random.nextInt(MIN_VALUE, MAX_VALUE)
                    remainingAttempts = ATTEMPT_COUNT
                    userGuess = TextFieldValue()
                    gameMessage = ""
                    isGameStarted = true
                }
            ) { Text("Oyuna Başla") }
        } else {

            if (remainingAttempts != 0) {
                Text(
                    "1 ile 100 arasında bir sayı tahmin edin.",
                    style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = userGuess,
                    onValueChange = { userGuess = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    enabled = remainingAttempts > 0,
                    onClick = {
                        val guess = userGuess.text.toIntOrNull()
                        if (guess != null && guess >= MIN_VALUE && guess < MAX_VALUE) {
                            remainingAttempts--
                            if (guess == targetNumber) {
                                gameMessage = "Tebrikler, kazandınız!"
                                remainingAttempts = 0
                            } else if (remainingAttempts == 0) {
                                gameMessage = "Oyun bitti kaybettiniz.\nDoğru cevap: $targetNumber"
                            } else if (guess < targetNumber) {
                                gameMessage =
                                    "Daha yüksek bir sayı deneyin\nKalan deneme hakkınız: $remainingAttempts"
                            } else {
                                gameMessage =
                                    "Daha düşük bir sayı deneyin.\nKalan deneme hakkınız: $remainingAttempts"
                            }
                        } else {
                            gameMessage = "Geçersiz giriş. Lütfen geçerli bir sayı girin."
                        }
                        userGuess = TextFieldValue()
                    },
                ) { Text("Tahmin Et") }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = gameMessage,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if (gameMessage.startsWith("Tebrikler")) Color.Green else Color.Red,
                        textAlign = TextAlign.Center
                    )
                )
            } else {
                Text(
                    text = gameMessage,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Green,
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        targetNumber = Random.nextInt(MIN_VALUE, MAX_VALUE)
                        remainingAttempts = ATTEMPT_COUNT
                        userGuess = TextFieldValue()
                        gameMessage = ""
                        isGameStarted = true
                    }
                ) { Text("Tekrar Dene") }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GamePreview() {
    NumberGuessGame()
}