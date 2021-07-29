package com.demin.konturtest.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.demin.konturtest.R
import com.demin.konturtest.common.entity.Contact
import java.text.SimpleDateFormat
import java.util.*

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(view)
        inflateViews(view)
    }

    private fun initToolbar(view: View) {
        activity?.let { activity ->
            activity as MainActivity
            activity.setSupportActionBar(view.findViewById(R.id.fr_details_toolbar))
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            activity.supportActionBar?.setHomeButtonEnabled(true)
            activity.supportActionBar?.setTitle(R.string.empty_string)
        }
    }

    private fun inflateViews(view: View) {
        val contact = requireArguments().getParcelable(CONTACT_KEY) as Contact?
        contact?.apply {
            view.findViewById<TextView>(R.id.fr_details_name).text = name
            view.findViewById<TextView>(R.id.fr_details_phone_number).apply {
                text = phone
                setOnClickListener {
                    requireContext().startActivity(
                        Intent(
                            Intent.ACTION_DIAL,
                            Uri.parse("tel:${phone}")
                        )
                    )
                }
            }
            view.findViewById<TextView>(R.id.fr_details_temperament).text = temperament
            view.findViewById<TextView>(R.id.fr_details_educationPeriod).apply {
                val inputDate = SimpleDateFormat(INPUT_DATE_FORMAT, Locale.getDefault())
                val outDate = SimpleDateFormat(OUT_DATE_FORMAT, Locale.getDefault())
                val start = inputDate.parse(educationPeriod.start)
                val end = inputDate.parse(educationPeriod.end)
                text = getString(
                    R.string.education_period_concat,
                    outDate.format(start!!),
                    outDate.format(end!!)
                )
            }
            view.findViewById<TextView>(R.id.fr_details_biography).text = biography
        }

    }

    companion object {
        private const val CONTACT_KEY = "Contact Key"
        private const val INPUT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss-hh:mm"
        private const val OUT_DATE_FORMAT = "dd.MM.yyyy"

        @JvmStatic
        fun newInstance(contact: Contact) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(CONTACT_KEY, contact)
            }
        }
    }
}