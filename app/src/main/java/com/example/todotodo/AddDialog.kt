package com.example.todotodo

import android.content.Context
import com.example.todotodo.library.dateIntToStr
import com.example.todotodo.library.dateStrToDate
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.todotodo.library.showToast
import com.example.todotodo.databinding.DialogAddBinding
import com.example.todotodo.listener.CustomDialogInterface
import com.example.todotodo.listener.setOnSingleClickListener
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class AddDialog(private val _context: Context, private val customDialogInterface: CustomDialogInterface) : DialogFragment() {

    private lateinit var binding: DialogAddBinding

    private var currentDate: Date = Date()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DialogAddBinding.inflate(inflater, container, false)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        isCancelable = false
        composeUI()

        return binding.root
    }

    private fun composeUI() {
        val today = SimpleDateFormat("yyyy / MM / dd").format(currentDate)
        binding.dateText.text = today

        binding.calendar.setOnDateChangeListener { _, year, month, day ->
            val date = dateIntToStr(year, month + 1, day)

            if (date < today) {
                showToast(_context, "이전 날짜는 선택하실 수 없습니다!")
                binding.calendar.date = currentDate.time
                return@setOnDateChangeListener
            }

            currentDate = dateStrToDate(date)
            binding.dateText.text = date
        }

        binding.addBtn.setOnSingleClickListener {
            val contents = binding.editText.text.toString()
            if (contents == "") {
                showToast(_context, "할일의 내용을 입력해주세요.")
                return@setOnSingleClickListener
            }

            customDialogInterface.onAddButtonClicked(
                currentDate.toString(),
                contents,
                LocalDateTime.now().toString()
            )

            dismiss()
        }

        binding.exitBtn.setOnClickListener { dismiss() }
    }
}