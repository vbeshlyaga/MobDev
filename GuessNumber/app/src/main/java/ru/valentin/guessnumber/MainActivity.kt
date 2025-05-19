package ru.valentin.guessnumber

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private var targetNumber = 0
    private var gameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hintText = findViewById<TextView>(R.id.hintText)
        val inputNumber = findViewById<EditText>(R.id.inputNumber)
        val guessButton = findViewById<Button>(R.id.guessButton)

        startNewGame()

        guessButton.setOnClickListener {
            if (gameOver) {
                startNewGame()
                hintText.text = getString(R.string.title_main)
                guessButton.text = getString(R.string.title_button)
                inputNumber.text.clear()
                return@setOnClickListener
            }

            val userInput = inputNumber.text.toString()

            if (userInput.isEmpty()) {
                Toast.makeText(this, getString(R.string.title_input), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val guess = userInput.toInt()

            when {
                guess < targetNumber -> {
                    hintText.text = "Загаданное число больше"
                }
                guess > targetNumber -> {
                    hintText.text = "Загаданное число меньше"
                }
                else -> {
                    hintText.text = "Поздравляем! Вы угадали!"
                    guessButton.text = "Начать новую игру"
                    gameOver = true
                }
            }

            inputNumber.text.clear()
        }
    }

    private fun startNewGame() {
        targetNumber = Random.nextInt(1, 101)
        gameOver = false
    }
}