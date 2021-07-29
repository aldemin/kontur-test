package com.demin.konturtest.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demin.konturtest.common.entity.Contact

class ContactListAdapter(private val mOnContactClick: (Contact) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mContactList = listOf<Contact>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ContactViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ContactViewHolder
        holder.bind(mContactList[position], mOnContactClick)
    }

    override fun getItemCount() = mContactList.size
}