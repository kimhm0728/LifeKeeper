package com.example.todotodo

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInit()

        toolbar = findViewById(R.id.toolbar)
        composeUI()
    }

    abstract fun onInit()

    abstract fun composeUI()

    fun composeToolbar() {
        setSupportActionBar(toolbar)
    }

    fun setHomeButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun setMenuTitle(titleResId: Int) {
        toolbar.title = resources.getString(titleResId)
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        menu.findItem(R.id.notification_item).isVisible = false
        menu.findItem(R.id.previous_item).isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected()")

        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private val TAG = BaseActivity::class.simpleName
    }
}