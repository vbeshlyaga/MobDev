package ru.valentin.student_journal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val groups = listOf(
        "БПИ-22-РП-1",
        "БПИ-22-РП-2",
        "БПИ-22-РП-3"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttons = listOf<Button>(
            findViewById(R.id.btn_group1),
            findViewById(R.id.btn_group2),
            findViewById(R.id.btn_group3)
        )

        buttons.forEachIndexed { index, button ->
            button.text = groups[index]
            button.setOnClickListener { openGroup(groups[index]) }
        }
    }

    private fun openGroup(group: String) {
        val intent = Intent(this, StudentListActivity::class.java).apply {
            putExtra("GROUP", group)
        }
        startActivity(intent)
    }
}