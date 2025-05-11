package ru.valentin.student_journal

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.valentin.student_journal.databinding.ItemStudentBinding

class StudentAdapter(
    private val onItemClick: (Student) -> Unit
) : ListAdapter<Student, StudentAdapter.StudentViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StudentViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class StudentViewHolder(
        private val binding: ItemStudentBinding,
        private val onItemClick: (Student) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(student: Student) {
            binding.apply {
                ivStudentPhoto.setImageResource(student.photoResId)
                tvStudentName.text = student.name
                tvMissedClasses.text = "Количество пропусков: ${student.missedClasses}"
                root.setOnClickListener { onItemClick(student) }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Student, newItem: Student) = oldItem == newItem
    }
}