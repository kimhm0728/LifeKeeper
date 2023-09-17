package com.example.todotodo.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todotodo.database.Todo
import com.example.todotodo.databinding.RecyclerviewItemBinding

class TodoRecyclerViewAdapter : RecyclerView.Adapter<TodoRecyclerViewAdapter.ViewHolder>() {

    private var userList = emptyList<Todo>()

    class ViewHolder(val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root)

    // 어떤 xml 으로 뷰 홀더를 생성할지 지정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // 뷰 홀더에 데이터를 바인딩
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.binding.dateText.text = currentItem.date
        holder.binding.contentsText.text = currentItem.contents
        holder.binding.postedText.text = currentItem.posted
    }

    // 뷰 홀더의 개수 리턴
    override fun getItemCount(): Int {
        return userList.size
    }

    // 유저 리스트 갱신
    fun setData(user : List<Todo>){
        userList = user
        notifyDataSetChanged()
    }
}