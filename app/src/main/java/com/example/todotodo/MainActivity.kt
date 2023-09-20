package com.example.todotodo

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todotodo.database.Todo
import com.example.todotodo.database.TodoDatabase
import com.example.todotodo.mvvm.TodoViewModel
import com.example.todotodo.databinding.ActivityMainBinding
import com.example.todotodo.listener.CustomDialogInterface
import com.example.todotodo.recyclerview.TodoRecyclerViewAdapter

class MainActivity : BaseActivity(), CustomDialogInterface {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: TodoDatabase
    private lateinit var todoViewModel: TodoViewModel

    override fun onInit() {
        installSplashScreen()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        db = TodoDatabase.getInstance(applicationContext) as TodoDatabase

        todoViewModel = ViewModelProvider(this, TodoViewModel.Factory(application))[TodoViewModel::class.java]
    }

    override fun composeUI() {
        super.composeToolbar()

        binding.addBtn.setOnClickListener {
            AddDialog(this, this).show()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        val adapter = TodoRecyclerViewAdapter()

        binding.recyclerView.adapter = adapter
        todoViewModel.allTodoList?.observe(this) {
            adapter.setData(it)
        }
    }

    override fun onAddButtonClicked(date: String, contents: String, posted: String) {
        val todo = Todo(date, contents, posted)
        todoViewModel.insert(todo)
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