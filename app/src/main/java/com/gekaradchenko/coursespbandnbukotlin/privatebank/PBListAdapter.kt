package com.gekaradchenko.coursespbandnbukotlin.privatebank

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gekaradchenko.coursespbandnbukotlin.databinding.RecyclerItemForPrivateBankBinding

class PBListAdapter(private val clickListener: PBListener) :
    ListAdapter<PBCourse, PBListAdapter.PBViewHolder>(DiffCallback) {

    private object DiffCallback : DiffUtil.ItemCallback<PBCourse>() {
        override fun areItemsTheSame(oldItem: PBCourse, newItem: PBCourse): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PBCourse, newItem: PBCourse): Boolean {
            return oldItem.saleRateNB == newItem.saleRateNB
        }

    }

    class PBViewHolder(private val binding: RecyclerItemForPrivateBankBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pbCourse: PBCourse, clickListener: PBListener) {

            binding.pbCourse = pbCourse
            binding.pbClickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PBViewHolder {
        return PBViewHolder(RecyclerItemForPrivateBankBinding.inflate(LayoutInflater.from(
            parent.context
        )))
    }

    override fun onBindViewHolder(holder: PBViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }


}

class PBListener(val clickListener: (currency: String) -> Unit) {
    fun onClick(pbCourse: PBCourse) = pbCourse.currency?.let { clickListener(it) }
}
