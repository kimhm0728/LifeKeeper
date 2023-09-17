package com.example.todotodo

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.todotodo.databinding.ActivityNotificationBinding

class NotificationActivity : BaseActivity() {

    private lateinit var binding: ActivityNotificationBinding

    override fun onInit() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)
    }

    override fun composeUI() {
        super.composeToolbar()
        setMenuTitle(R.string.notification)
        setHomeButton()
    }
}