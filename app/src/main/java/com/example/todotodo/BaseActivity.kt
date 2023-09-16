package com.example.todotodo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout

abstract class BaseActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(onInit())

        toolbar = findViewById(R.id.toolbar)
        composeToolbar()
    }

    abstract fun onInit() : ConstraintLayout

    open fun composeToolbar() {
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
        menu.findItem(R.id.add_item).isVisible = false
        menu.findItem(R.id.notification_item).isVisible = false
        menu.findItem(R.id.previous_item).isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}