package com.example.todotodo

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.todotodo.databinding.ActivityPreviousBinding

class PreviousActivity : BaseActivity() {

    private lateinit var binding: ActivityPreviousBinding

    override fun onInit() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_previous)

    }

    override fun composeUI() {
        super.composeToolbar()
        setMenuTitle(R.string.previous_items)
        setHomeButton()
    }
}