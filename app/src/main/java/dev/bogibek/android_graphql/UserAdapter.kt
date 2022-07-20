package dev.bogibek.android_graphql

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.bogibek.android_graphql.databinding.ItemUserBinding

class UserAdapter : ListAdapter<UsersListQuery.User, UserAdapter.UserViewHolder>(Comparator) {

    var itemClick: ((UsersListQuery.User) -> Unit)? = null

    object Comparator : DiffUtil.ItemCallback<UsersListQuery.User>() {
        override fun areItemsTheSame(
            oldItem: UsersListQuery.User,
            newItem: UsersListQuery.User,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UsersListQuery.User,
            newItem: UsersListQuery.User,
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UsersListQuery.User?) {
            binding.apply {
                tvName.text = user?.name.toString()
                tvTwitter.text = user?.twitter.toString()
                tvRocket.text = user?.rocket.toString()

                root.setOnClickListener {
                    itemClick?.invoke(user!!)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
}