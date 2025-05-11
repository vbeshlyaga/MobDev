package ru.valentin.my_tic_tac_toe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.valentin.my_tic_tac_toe.databinding.ActivityMainBinding

const val EXTRA_TIME = "ru.valentin.mytictactoe.TIME"
const val EXTRA_GAME_FIELD = "ru.valentin.mytictactoe.GAME_FIELD"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_My_Tic_Tac_Toe)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.toNewGame.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        binding.toContinueGame.setOnClickListener {
            val gameInfo = getInfoAboutLastGame()
            val intent = Intent(this, GameActivity::class.java).apply {
                putExtra(EXTRA_TIME, gameInfo.time)
                putExtra(EXTRA_GAME_FIELD, gameInfo.gameField)
            }
            startActivity(intent)
        }

        binding.toSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
    }

    private fun getInfoAboutLastGame() : GameInfo {
        with(getSharedPreferences("game", MODE_PRIVATE)){
            val time = getLong("time", 0)
            val gameField = getString("gameField", "")

            return if (gameField != null) {
                GameInfo(time, gameField)
            } else {
                GameInfo(0,"")
            }
        }
    }

    data class GameInfo(val time: Long, val gameField: String)
}