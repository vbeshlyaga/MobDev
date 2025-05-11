package ru.valentin.student_journal

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(attendance: Attendance)

    @Query("SELECT * FROM attendance WHERE studentId = :studentId AND date = :date")
    suspend fun getByDate(studentId: Int, date: String): Attendance?

    @Query("SELECT COUNT(*) FROM attendance WHERE studentId = :studentId AND status = 'Absent'")
    suspend fun getAbsentCount(studentId: Int): Int

    @Query("SELECT * FROM attendance WHERE studentId = :studentId")
    fun getStudentAttendance(studentId: Int): Flow<List<Attendance>>
}