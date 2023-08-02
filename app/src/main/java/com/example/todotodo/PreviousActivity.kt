package com.example.todotodo

class PreviousActivity : BaseActivity() {
    override fun onInit() {
        setContentView(R.layout.activity_previous)
    }

    override fun composeToolbar() {
        super.composeToolbar()
        setMenuTitle(R.string.previous_items)
        setHomeButton()
    }
}