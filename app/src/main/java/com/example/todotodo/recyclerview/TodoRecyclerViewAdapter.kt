package com.example.todotodo.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todotodo.database.Todo
import com.example.todotodo.databinding.RecyclerViewItemBinding

class TodoRecyclerViewAdapter : RecyclerView.Adapter<TodoRecyclerViewAdapter.ViewHolder>() {

    private lateinit var binding: RecyclerViewItemBinding
    private var todoList = emptyList<Todo>()

    class ViewHolder(val binding: RecyclerViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = todoList[position]
        holder.bind(currentItem)

        binding.root.setOnClickListener {
            Log.e(TAG, "$currentItem.id")
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun setData(todo : List<Todo>){
        todoList = todo
        notifyDataSetChanged()
    }

    companion object {
        private val TAG = TodoRecyclerViewAdapter::class.simpleName
    }
}