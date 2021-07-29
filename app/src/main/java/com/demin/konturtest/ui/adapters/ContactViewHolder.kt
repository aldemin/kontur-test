package com.demin.konturtest.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.demin.konturtest.R
import com.demin.konturtest.common.entity.Contact

class ContactViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.item_contact,
        parent,
        false
    )
) {

    private val mName: TextView = itemView.findViewById(R.id.name)
    private val mPhoneNumber: TextView = itemView.findViewById(R.id.phone_number)
    private val mHeight: TextView = itemView.findViewById(R.id.height)

    fun bind(contact: Contact, onContactClick: (Contact) -> Unit) {
        mName.text = contact.name
        mPhoneNumber.text = contact.phone
        mHeight.text = contact.height.toString()
        itemView.setOnClickListener {
            onContactClick.invoke(contact)
        }
    }

}