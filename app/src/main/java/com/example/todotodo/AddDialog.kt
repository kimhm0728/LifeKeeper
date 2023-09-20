package com.example.todotodo

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.example.todotodo.library.dateIntToStr
import com.example.todotodo.library.dateStrToDate
import android.os.Bundle
import com.example.todotodo.library.showToast
import com.example.todotodo.databinding.DialogAddBinding
import com.example.todotodo.listener.CustomDialogInterface
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class AddDialog(private val _context: Context, private val customDialogInterface: CustomDialogInterface) : Dialog(_context) {

    private lateinit var binding: DialogAddBinding

    private var currentDate: Date = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_add)
        binding = DialogAddBinding.inflate(layoutInflater)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        composeUI()
    }

    private fun composeUI() {
        val today = SimpleDateFormat("yyyy / MM / dd").format(currentDate)
        binding.dateText.text = today

        binding.calendar.setOnDateChangeListener { _, year, month, day ->
            val date = dateIntToStr(year, month + 1, day)

            if (date < today) {
                showToast(_context, "이전 날짜는 선택하실 수 없습니다!")
                binding.calendar.date = (currentDate ?: Date()).time
                return@setOnDateChangeListener
            }

            currentDate = dateStrToDate(date)
            binding.dateText.text = date
        }

        binding.addBtn.setOnClickListener {
            val contents = binding.editText.text.toString()
            if (contents == "") {
                showToast(_context, "할일의 내용을 입력해주세요.")
                return@setOnClickListener
            }

            customDialogInterface.onAddButtonClicked(currentDate.toString(), contents, LocalDateTime.now().toString())
            dismiss()
        }

        binding.exitBtn.setOnClickListener { dismiss() }
    }

    override fun onBackPressed() {
        //super.onBackPressed
    }
}