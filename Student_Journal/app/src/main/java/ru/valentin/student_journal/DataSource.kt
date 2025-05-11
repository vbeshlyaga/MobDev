package ru.valentin.student_journal

object DataSource {
    private val groups = listOf(
        "БПИ-22-РП-1",
        "БПИ-22-РП-2",
        "БПИ-22-РП-3"
    )

    private val students = listOf(
        // Группа 1
        Student(1, "Иванов Иван", groups[0], R.drawable.ic_student),
        Student(2, "Петрова Анна", groups[0], R.drawable.ic_student),
        Student(3, "Сидоров Алексей", groups[0], R.drawable.ic_student),

        // Группа 2
        Student(4, "Козлова Мария", groups[1], R.drawable.ic_student),
        Student(5, "Новиков Дмитрий", groups[1], R.drawable.ic_student),
        Student(6, "Федорова Ольга", groups[1], R.drawable.ic_student),

        // Группа 3
        Student(7, "Бешляга Валентин", groups[2], R.drawable.ic_student),
        Student(8, "Готшалк Диана Евгеньевна", groups[2], R.drawable.ic_student),
        Student(9, "Калашникова Анастасия Павловна", groups[2], R.drawable.ic_student),
        Student(10, "Лысенко Константин Андреевич", groups[2], R.drawable.ic_student),
        Student(11, "Мирная Алена Витальевна", groups[2], R.drawable.ic_student),
        Student(12, "Ри Дю Ен", groups[2], R.drawable.ic_student),
        Student(13, "Хомяк Мария Александровна", groups[2], R.drawable.ic_student)
    )

    fun getStudentsByGroup(group: String) = students.filter { it.group == group }
}