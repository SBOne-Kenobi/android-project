package com.rustamsadykov.firstapp

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    var userList : List<User> = emptyList()
    var testAvatar: Bitmap? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val userItemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(userItemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (testAvatar == null)
            holder.avatarImageView.setImageResource(R.mipmap.ic_launcher)
        else
            holder.avatarImageView.setImageBitmap(testAvatar)
        holder.userNameTextView.text = userList[position].userName
        holder.groupNameTextView.text = userList[position].groupName
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
        val groupNameTextView: TextView = itemView.findViewById(R.id.groupNameTextView)

    }
}
