package com.example.todotodo

import androidx.constraintlayout.widget.ConstraintLayout
import com.example.todotodo.databinding.ActivityNotificationBinding

class NotificationActivity : BaseActivity() {

    private lateinit var binding: ActivityNotificationBinding

    override fun onInit() : ConstraintLayout {
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun composeToolbar() {
        super.composeToolbar()
        setMenuTitle(R.string.notification)
        setHomeButton()
    }
}