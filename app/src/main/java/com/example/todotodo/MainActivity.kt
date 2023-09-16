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

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_item -> {
                // TODO 항목 추가하는 다이얼로그
                // TODO add 아이콘을 home 버튼으로 옮기기(home 버튼 벡터 불러오기 오류)
                true
            }
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