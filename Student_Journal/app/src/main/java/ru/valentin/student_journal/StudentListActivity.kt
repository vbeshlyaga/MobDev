package ru.valentin.student_journal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.valentin.student_journal.databinding.ActivityStudentListBinding

class StudentListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentListBinding
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = AppDatabase.getDatabase(this)

        setupRecyclerView()
        loadStudents()
    }

    private fun setupRecyclerView() {
        studentAdapter = StudentAdapter { student ->
            openStudentProfile(student)
        }
        binding.rvStudents.apply {
            layoutManager = LinearLayoutManager(this@StudentListActivity)
            adapter = studentAdapter
        }
    }

    private fun loadStudents() {
        val group = intent.getStringExtra("GROUP") ?: return
        lifecycleScope.launch {
            val students = DataSource.getStudentsByGroup(group).map { student ->
                student.copy(missedClasses = db.attendanceDao().getAbsentCount(student.id))
            }
            studentAdapter.submitList(students)
        }
    }

    private fun openStudentProfile(student: Student) {
        startActivityForResult(
            Intent(this, StudentProfileActivity::class.java).apply {
                putExtra("STUDENT", student)
            },
            REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            loadStudents() // Обновляем список при возвращении
        }
    }

    companion object {
        private const val REQUEST_CODE = 1001
    }
}