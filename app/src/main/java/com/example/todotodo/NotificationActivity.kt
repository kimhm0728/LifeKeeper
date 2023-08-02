package com.example.todotodo

class NotificationActivity : BaseActivity() {
    override fun onInit() {
        setContentView(R.layout.activity_notification)
    }

    override fun composeToolbar() {
        super.composeToolbar()
        setMenuTitle(R.string.notification)
        setHomeButton()
    }
}