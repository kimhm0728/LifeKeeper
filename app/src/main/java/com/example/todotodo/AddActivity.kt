package com.example.todotodo

import android.graphics.Rect
import com.example.todotodo.library.dateIntToStr
import com.example.todotodo.library.dateStrToDate
import android.os.Bundle
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import com.example.todotodo.database.Todo
import com.example.todotodo.database.TodoDatabase
import com.example.todotodo.databinding.ActivityAddBinding
import com.example.todotodo.library.convertDPtoPX
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private var currentDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        composeUI()
    }

    private fun composeUI() {
        currentDate = Date()
        val today = SimpleDateFormat("yyyy / MM / dd").format(currentDate)
        binding.dateText.text = today

        binding.calendar.setOnDateChangeListener { _, year, month, day ->
            val date = dateIntToStr(year, month + 1, day)

            if (date < today) {
                showToast("이전 날짜는 선택하실 수 없습니다!")
                binding.calendar.date = (currentDate ?: Date()).time
                return@setOnDateChangeListener
            }

            currentDate = dateStrToDate(date)
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

        binding.exitBtn.run {
            setOnClickListener { finish() }

            val size = 12.convertDPtoPX(context)
            val parent = parent as View
            parent.doOnPreDraw { parent ->
                val updateRect = Rect().also { r ->
                    this.getHitRect(r)
                    r.inset(size, size)
                }

                parent.touchDelegate = TouchDelegate(updateRect, this)
            }
        }
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