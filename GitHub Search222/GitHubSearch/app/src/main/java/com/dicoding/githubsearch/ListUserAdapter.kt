package com.dicoding.githubsearch

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubsearch.databinding.ItemRowUserBinding

class ListUserAdapter(private val listUser: List<User>) : RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private val userData = ArrayList<User>()

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    fun setData(items: ArrayList<User>) {
        userData.clear()
        userData.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
//        val tvItem: TextView = view.findViewById(R.id.tvItem)
//        val tvImage: ImageView = view.findViewById(R.id.imgPhoto)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
//        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false)
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        viewHolder.tvItem.text = listReview[position]
        val (name, photo) = listUser[position]
        Glide.with(viewHolder.itemView.context)
            .load(photo)
            .into(viewHolder.binding.imgPhoto)
        viewHolder.binding.tvItem.text = name
        Log.e(TAG, "onBindViewHolder: login")
        viewHolder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[viewHolder.adapterPosition]) }
    }

    override fun getItemCount() = listUser.size

    companion object {
        private const val TAG = "ListUserAdapter"
    }
}
/*

package com.dicoding.githubuser

import android.view.LayoutInflater
//import android.view.View
import android.view.ViewGroup
*/
/*import android.widget.ImageView
import android.widget.TextView*//*

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.databinding.ItemRowUserBinding

class ListUserAdapter(private val listUser: List<User>): RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private val userData = ArrayList<User>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    fun setData(items: ArrayList<User>) {
        userData.clear()
        userData.addAll(items)
        notifyDataSetChanged()
    }

    class ListViewHolder(var binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root) {
        */
/*var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvCompany: TextView = itemView.findViewById(R.id.tv_item_company)*//*

    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        //val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user,viewGroup, false)
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, photo) = listUser[position]
        Glide.with(holder.itemView.context)
            .load(photo)
            .into(holder.binding.imgItemPhoto)
        holder.binding.tvItemName.text = name
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listUser.size

}*/
