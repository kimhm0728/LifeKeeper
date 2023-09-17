package com.example.todotodo

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.todotodo.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onInit(): ConstraintLayout {
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun composeUI() {
        super.composeToolbar()

        binding.addBtn.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.notification_item -> {
                startActivity(Intent(this, NotificationActivity::class.java))
                true
            }
            R.id.previous_item -> {
                startActivity(Intent(this, PreviousActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}