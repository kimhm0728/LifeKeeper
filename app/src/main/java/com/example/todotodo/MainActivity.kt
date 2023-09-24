package com.example.todotodo

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todotodo.database.Todo
import com.example.todotodo.database.TodoDatabase
import com.example.todotodo.mvvm.TodoViewModel
import com.example.todotodo.databinding.ActivityMainBinding
import com.example.todotodo.library.showToast
import com.example.todotodo.dialog.CustomDialogInterface
import com.example.todotodo.library.setOnSingleClickListener
import com.example.todotodo.recyclerview.TodoRecyclerViewAdapter
import com.example.todotodo.dialog.AddDialog

class MainActivity : BaseActivity(), CustomDialogInterface {

    private lateinit var context: Context
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: TodoDatabase
    private lateinit var todoViewModel: TodoViewModel

    override fun onInit() {
        installSplashScreen()

        context = this

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        db = TodoDatabase.getInstance(applicationContext) as TodoDatabase
        todoViewModel = ViewModelProvider(this, TodoViewModel.Factory(application))[TodoViewModel::class.java]

        addOnBackPressedCallback()
    }

    override fun composeUI() {
        super.composeToolbar()

        binding.addBtn.setOnSingleClickListener {
            Log.d(TAG, "AddDialog show")
            AddDialog().show(supportFragmentManager, DIALOG_TAG)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        val adapter = TodoRecyclerViewAdapter()

        binding.recyclerView.adapter = adapter
        todoViewModel.allTodoList?.observe(this) {
            adapter.setData(it)
        }
    }

    override fun onAddButtonClicked(date: String, contents: String, posted: String) {
        Log.e(TAG, "onAddButtonClicked() $date $contents $posted")

        val todo = Todo(date, contents, posted)
        todoViewModel.insert(todo)
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected()")

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

    private fun addOnBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d(TAG, "handleOnBackPressed()")

                if (System.currentTimeMillis() - backPressedTime >= 2000) {
                    backPressedTime = System.currentTimeMillis()
                    showToast(context, resources.getString(R.string.back_key))
                } else if (System.currentTimeMillis() - backPressedTime < 2000) {
                    finish()
                }
            }
        }

        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
        private var backPressedTime: Long = 0

        private const val DIALOG_TAG = "dialog"
    }
}