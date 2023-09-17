package com.example.todotodo

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todotodo.database.TodoDatabase
import com.example.todotodo.mvvm.TodoViewModel
import com.example.todotodo.databinding.ActivityMainBinding
import com.example.todotodo.recyclerview.TodoRecyclerViewAdapter

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: TodoDatabase
    private lateinit var todoViewModel: TodoViewModel

    override fun onInit() {
        installSplashScreen()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        db = (TodoDatabase.getInstance(applicationContext) ?: null) as TodoDatabase

        todoViewModel = ViewModelProvider(this, TodoViewModel.Factory(application))[TodoViewModel::class.java]
    }

    override fun composeUI() {
        super.composeToolbar()

        binding.addBtn.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }

        // 아이템을 가로로 하나씩 보여줌
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        // 어댑터 연결
        val adapter = TodoRecyclerViewAdapter()
        binding.recyclerView.adapter = adapter

        todoViewModel.allTodoList?.observe(this, Observer {
            adapter.setData(it)
        })
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