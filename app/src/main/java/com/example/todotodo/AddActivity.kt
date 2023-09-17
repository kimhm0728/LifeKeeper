package com.example.todotodo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todotodo.database.Todo
import com.example.todotodo.database.TodoDatabase
import com.example.todotodo.databinding.ActivityAddBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        composeUI()
    }

    private fun composeUI() {
        val today = SimpleDateFormat("yyyy / MM / dd").format(Date())
        binding.dateText.text = today

        binding.calendar.setOnDateChangeListener { _, year, month, day ->
            val date = getDateStr(year, month + 1, day)

            if (date < today) {
                showToast("이전 날짜는 선택하실 수 없습니다!")
                return@setOnDateChangeListener
            }

            binding.dateText.text = date
        }

        binding.addBtn.setOnClickListener {
            val contents = binding.editText.text.toString()
            if (contents == "") {
                showToast("할일의 내용을 입력해주세요.")
                return@setOnClickListener
            }

            val newTodo = Todo("date", contents, LocalDateTime.now().toString())
            val db = TodoDatabase.getInstance(applicationContext)

            CoroutineScope(Dispatchers.IO).launch {
                db?.todoDao()?.insert(newTodo)
            }
        }
    }

    private fun getDateStr(year: Int, month: Int, day: Int): String {
        var date = "$year / "

        if (month < 10) date += "0"
        date += "$month / "

        if (day < 10) date += "0"
        date += day

        return date
    }

    override fun onBackPressed() {
        //super.onBackPressed
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