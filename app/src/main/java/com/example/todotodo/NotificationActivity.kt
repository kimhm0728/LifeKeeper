package com.example.todotodo

import android.util.Log
import android.widget.Button
import android.widget.TimePicker
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.DataBindingUtil
import com.example.todotodo.databinding.ActivityNotificationBinding
import com.example.todotodo.preference.PreferenceUtil
import com.example.todotodo.preference.TodoTodoApplication

class NotificationActivity : BaseActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private lateinit var pref: PreferenceUtil

    private var switch: Boolean? = null
    private var hour: Int? = null
    private var minute: Int? = null

    private var switchCompat: SwitchCompat? = null
    private var timePicker: TimePicker? = null
    private var applyBtn: Button? = null

    override fun onInit() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)
        pref = TodoTodoApplication.prefs
    }

    override fun composeUI() {
        super.composeToolbar()
        setMenuTitle(R.string.notification)
        setHomeButton()

        switchCompat = binding.switchCompat
        timePicker = binding.timePicker
        applyBtn = binding.applyBtn

        switchCompat?.isChecked = run {
            val c = pref.getData(SWITCH_KEY, DEFAULT_KEY)
            Log.d(TAG, "Switch isChecked $c")
            if (c == DEFAULT_KEY) false else c.toBoolean()
        }

        timePicker?.isEnabled = switchCompat?.isChecked == true

        timePicker?.hour = run {
            val h = pref.getData(HOUR_KEY, DEFAULT_KEY)
            Log.d(TAG, "TimePicker hour $h")
            if (h == DEFAULT_KEY) 12 else h.toInt()
        }

        timePicker?.minute = run {
            val m = pref.getData(MINUTE_KEY, DEFAULT_KEY)
            Log.d(TAG, "TimePicker minute $m")
            if (m == DEFAULT_KEY) 30 else m.toInt()
        }

        switchCompat?.setOnCheckedChangeListener { _, isCheck ->
            Log.d(TAG, "Switch checked change : $isCheck")

            switch = isCheck

            timePicker?.isEnabled = isCheck
            setApplyBtn()
        }

        timePicker?.setOnTimeChangedListener { _, h, m ->
            hour = h
            minute = m

            setApplyBtn()
        }

        applyBtn?.setOnClickListener {
            Log.d(TAG, "Apply Button clicked")

            pref.setData(SWITCH_KEY, switch.toString())
            pref.setData(HOUR_KEY, hour.toString())
            pref.setData(MINUTE_KEY, minute.toString())
            finish()
        }
    }

    private fun setApplyBtn() {
        applyBtn?.isEnabled = isChangedSetting()
    }

    private fun isChangedSetting(): Boolean {
        val prefSwitch = pref.getData(SWITCH_KEY, DEFAULT_KEY)
        if (prefSwitch != switch.toString()) return true

        val prefHour = pref.getData(HOUR_KEY, DEFAULT_KEY)
        val prefMinute = pref.getData(MINUTE_KEY, DEFAULT_KEY)

        if (prefHour != hour.toString() || prefMinute != minute.toString()) return true
        return false
    }

    companion object {
        private val TAG = NotificationActivity::class.simpleName

        private const val DEFAULT_KEY = "default"
        private const val SWITCH_KEY = "switch"
        private const val HOUR_KEY = "hour"
        private const val MINUTE_KEY = "minute"
    }
}