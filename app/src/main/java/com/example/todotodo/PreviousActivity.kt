package com.example.todotodo

import androidx.constraintlayout.widget.ConstraintLayout
import com.example.todotodo.databinding.ActivityPreviousBinding

class PreviousActivity : BaseActivity() {

    private lateinit var binding: ActivityPreviousBinding

    override fun onInit() : ConstraintLayout {
        binding = ActivityPreviousBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun composeToolbar() {
        super.composeToolbar()
        setMenuTitle(R.string.previous_items)
        setHomeButton()
    }
}