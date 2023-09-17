package com.example.todotodo

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todotodo.database.Todo
import com.example.todotodo.database.TodoDatabase
import com.example.todotodo.databinding.ActivityAddBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        composeUI()
    }

    private fun composeUI() {
        val today = LocalDate.now().toString()
        binding.dateText.text = today

        binding.calendar.setOnDateChangeListener { _, year, month, day ->
            val date = "$year-${month + 1}-$day"
            binding.dateText.text = date

            if (date < today) {
                showToast("이전 날짜는 선택하실 수 없습니다!")
            }
        }

        binding.addBtn.setOnClickListener {
            val contents = binding.editText.text.toString()
            if (contents == "") {
                showToast("할일 내용을 입력해주세요.")
                return@setOnClickListener
            }

            val newTodo = Todo("date", contents, LocalDateTime.now().toString())
            val db = TodoDatabase.getInstance(applicationContext)

            CoroutineScope(Dispatchers.IO).launch {
                db?.todoDao()?.insert(newTodo)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_OUTSIDE) {
            return false
        }

        return super.onTouchEvent(event)
    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }
}