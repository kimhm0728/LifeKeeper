package com.example.todotodo.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.todotodo.R
import com.example.todotodo.databinding.DialogAddBinding
import com.example.todotodo.library.*
import java.util.Date

class AddDialog : DialogFragment() {

    private lateinit var binding: DialogAddBinding
    private lateinit var mContext: Context

    private var currentDate: Date = Date()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DialogAddBinding.inflate(inflater, container, false)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        isCancelable = false
        composeUI()

        return binding.root
    }

    private fun composeUI() {
        val today = currentDate.toDateString()
        binding.dateText.text = today

        binding.calendar.setOnDateChangeListener { _, year, month, day ->
            Log.d(TAG, "Calendar date changed : $year ${month + 1} $day")

            val date = dateIntToStr(year, month + 1, day)

            if (date < today) {
                showToast(mContext, resources.getString(R.string.previous_date))
                binding.calendar.date = currentDate.time
                return@setOnDateChangeListener
            }

            currentDate = date.toDate()
            binding.dateText.text = date
        }

        binding.addBtn.setOnSingleClickListener {
            val contents = binding.editText.text.toString()
            if (contents == "") {
                showToast(mContext, resources.getString(R.string.contents_empty))
                return@setOnSingleClickListener
            }

            (activity as CustomDialogInterface).onAddButtonClicked(
                binding.dateText.text.toString(),
                contents,
                Date().toPostedString()
            )

            dismiss()
        }

        binding.exitBtn.setOnClickListener { dismiss() }
    }

    companion object {
        private val TAG = AddDialog::class.simpleName
    }
}