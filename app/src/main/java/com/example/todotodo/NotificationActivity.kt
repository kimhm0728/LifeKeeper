package com.example.todotodo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.todotodo.databinding.ActivityNotificationBinding
import com.example.todotodo.library.setOnSingleClickListener
import com.example.todotodo.notification.NotificationManager
import com.example.todotodo.preference.PreferenceUtil
import com.example.todotodo.preference.TodoApplication

class NotificationActivity : BaseActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private lateinit var pref: PreferenceUtil
    private lateinit var notificationManager: NotificationManager

    private var switch: Boolean = false
    private var hour: Int = 12
    private var minute: Int = 0

    private val switchCompat by lazy { binding.switchCompat }
    private val timePicker by lazy { binding.timePicker }
    private val applyBtn by lazy { binding.applyBtn }

    private val notiPermLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onInit() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)
        pref = TodoApplication.prefs
        notificationManager = NotificationManager(this)
    }

    override fun composeUI() {
        super.composeToolbar()
        setMenuTitle(R.string.notification)
        setHomeButton()

        if (Build.VERSION.SDK_INT >= 33 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_DENIED) {
            Log.e(TAG, "permission denied")
            binding.notiLayout.visibility = View.GONE
            binding.permLayout.visibility = View.VISIBLE
            notiPermLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)

            binding.permBtn.setOnSingleClickListener {
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName")).run {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(this)
                }
            }
            return
        }

        switchCompat.isChecked = run {
            val c = pref.getData(SWITCH_KEY, DEFAULT_KEY)
            Log.d(TAG, "Switch isChecked $c")
            if (c == DEFAULT_KEY) switch else c.toBoolean()
        }

        timePicker.isEnabled = switchCompat.isChecked == true

        timePicker.hour = run {
            val h = pref.getData(HOUR_KEY, DEFAULT_KEY)
            Log.d(TAG, "TimePicker hour $h")
            if (h == DEFAULT_KEY) hour else h.toInt()
        }

        timePicker.minute = run {
            val m = pref.getData(MINUTE_KEY, DEFAULT_KEY)
            Log.d(TAG, "TimePicker minute $m")
            if (m == DEFAULT_KEY) minute else m.toInt()
        }

        switchCompat.setOnCheckedChangeListener { _, isCheck ->
            Log.d(TAG, "Switch checked change : $isCheck")

            switch = isCheck

            timePicker.isEnabled = isCheck
            setApplyBtn()
        }

        timePicker.setOnTimeChangedListener { _, h, m ->
            hour = h
            minute = m

            setApplyBtn()
        }

        applyBtn.setOnClickListener {
            Log.d(TAG, "Apply Button clicked")

            pref.setData(SWITCH_KEY, switch.toString())
            pref.setData(HOUR_KEY, hour.toString())
            pref.setData(MINUTE_KEY, minute.toString())

            if (switchCompat.isChecked) {
                notificationManager.startNotification(hour, minute)
            } else {
                notificationManager.stopNotification()
            }

            finish()
        }
    }

    private fun setApplyBtn() {
        applyBtn.isEnabled = isChangedSetting()
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

        const val DEFAULT_KEY = "default"
        const val SWITCH_KEY = "switch"
        const val HOUR_KEY = "hour"
        const val MINUTE_KEY = "minute"
    }
}