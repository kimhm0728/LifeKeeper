package com.example.todotodo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat

abstract class BaseActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInit()

        toolbar = findViewById(R.id.toolbar)
        composeToolbar()
    }

    abstract fun onInit()

    open fun composeToolbar() {
        setSupportActionBar(toolbar)
    }

    fun setHomeButton(drawableResId: Int = R.drawable.ic_back) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // supportActionBar?.setHomeAsUpIndicator(drawableResId)
        // TODO Vector Asset 불러오지 못하는 현상 해결
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
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}