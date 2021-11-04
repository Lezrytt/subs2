package com.dicoding.githubsearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubsearch.databinding.ItemRowUserBinding

class FragmentUserAdapter : RecyclerView.Adapter<FragmentUserAdapter.ListViewHolder>() {
    private val UserData = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(items: ArrayList<User>) {
        UserData.clear()
        UserData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(UserData[position])
    }

    override fun getItemCount(): Int = UserData.size

    inner class ListViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(User: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(User.Photo)
                    .into(imgPhoto)

                tvItem.text = User.User
                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(User) }
            }

        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}