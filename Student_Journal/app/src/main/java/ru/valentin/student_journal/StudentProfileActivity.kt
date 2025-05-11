package ru.valentin.student_journal

import android.app.Activity
import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.valentin.student_journal.databinding.ActivityStudentProfileBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.max


class StudentProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentProfileBinding
    private lateinit var student: Student
    private lateinit var db: AppDatabase
    private var currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        student = intent.getParcelableExtra("STUDENT")!!
        db = AppDatabase.getDatabase(this)

        setupViews()
        loadInitialData()
    }

    private fun setupViews() {
        title = student.name
        binding.calendarView.setOnDateChangeListener { _, y, m, d ->
            currentDate = "${d.toString().padStart(2, '0')}-${(m+1).toString().padStart(2, '0')}-$y"
            updateAttendanceStatus()
        }

        binding.btnPresent.setOnClickListener {
            updateAttendance("Present", shouldDecrement = true)
        }

        binding.btnAbsent.setOnClickListener {
            updateAttendance("Absent", shouldDecrement = false)
        }
    }

    private fun loadInitialData() {
        lifecycleScope.launch {
            student.missedClasses = db.attendanceDao().getAbsentCount(student.id)
            updateMissedCounter()
            updateAttendanceStatus()
        }
    }

    private fun updateAttendance(status: String, shouldDecrement: Boolean) {
        lifecycleScope.launch(Dispatchers.IO) {
            // Получаем предыдущий статус
            val previousAttendance = db.attendanceDao().getByDate(student.id, currentDate)

            // Если статус уже "Absent" и снова нажали "Absent"
            if (status == "Absent" && previousAttendance?.status == "Absent") {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@StudentProfileActivity, "Студент уже отмечен как отсутствующий", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }

            // Вставляем новую запись
            db.attendanceDao().insert(
                Attendance(
                    studentId = student.id,
                    date = currentDate,
                    status = status
                )
            )

            // Обновляем счетчик пропусков
            if (status == "Present" && shouldDecrement && previousAttendance?.status == "Absent") {
                student.missedClasses = max(0, student.missedClasses - 1)
            } else if (status == "Absent" && previousAttendance?.status != "Absent") {
                student.missedClasses++
            }

            withContext(Dispatchers.Main) {
                updateMissedCounter()
                binding.tvStatus.text = when (status) {
                    "Present" -> "Присутствовал"
                    "Absent" -> "Отсутствовал"
                    else -> "Статус не указан"
                }
                setResult(RESULT_OK)
            }
        }
    }

    private fun updateMissedCounter() {
        binding.tvMissed.text = "Количестов пропусков: ${student.missedClasses}"
    }

    private fun updateAttendanceStatus() {
        lifecycleScope.launch {
            val attendance = db.attendanceDao().getByDate(student.id, currentDate)
            binding.tvStatus.text = when(attendance?.status) {
                "Present" -> "Присутствовал"
                "Absent" -> "Отсутствовал"
                else -> "Статус не указан"
            }
        }
    }


}