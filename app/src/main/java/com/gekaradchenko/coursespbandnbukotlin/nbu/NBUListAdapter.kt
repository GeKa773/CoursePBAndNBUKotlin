package com.gekaradchenko.coursespbandnbukotlin.nbu

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gekaradchenko.coursespbandnbukotlin.R
import com.gekaradchenko.coursespbandnbukotlin.databinding.RecyclerItemForNbuBinding


class NBUListAdapter(private val clickListener: NBUListener, private val context: Context) :
    ListAdapter<NBUCourse, NBUListAdapter.NBUViewHolder>(NBUListAdapter.DiffCallback) {


    private object DiffCallback : DiffUtil.ItemCallback<NBUCourse>() {
        override fun areItemsTheSame(oldItem: NBUCourse, newItem: NBUCourse): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: NBUCourse, newItem: NBUCourse): Boolean {
            return oldItem.isSelected == newItem.isSelected
        }
    }

    class NBUViewHolder(private val binding: RecyclerItemForNbuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(nbuCourse: NBUCourse, clickListener: NBUListener) {
            binding.nbuCourse = nbuCourse
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NBUViewHolder {
        return NBUViewHolder(RecyclerItemForNbuBinding.inflate(LayoutInflater.from(
            parent.context
        )))
    }

    override fun onBindViewHolder(holder: NBUViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
//        holder.itemView.background = R.color.color_for_second_recycler_view_item
        when {
            getItem(position).isSelected -> holder.itemView.setBackgroundColor(context.getColor(R.color.color_for_selected_recycler_view_item))

            position % 2 != 0 -> holder.itemView.setBackgroundColor(context.getColor(R.color.color_for_second_recycler_view_item))

            else -> holder.itemView.setBackgroundColor(context.getColor(R.color.color_for_first_recycler_view_item))

        }

    }
}

class NBUListener(val clickListener: (currency: String) -> Unit) {
    fun onClick(nbuCourse: NBUCourse) = clickListener(nbuCourse.cc)
}