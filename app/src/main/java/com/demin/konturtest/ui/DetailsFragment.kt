package com.demin.konturtest.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.demin.konturtest.R
import com.demin.konturtest.common.appComponent
import com.demin.konturtest.common.entity.Contact
import com.demin.konturtest.ui.viewmodel.DetailsFragmentViewModel
import javax.inject.Inject

class DetailsFragment : Fragment() {

    @Inject
    lateinit var factory: DetailsFragmentViewModel.Factory

    private val viewModel: DetailsFragmentViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(view)
        initViews(view)
    }

    private fun initToolbar(view: View) {
        activity?.let { activity ->
            activity.apply {
                setActionBar(view.findViewById(R.id.fr_details_toolbar))
                actionBar?.apply {
                    setDisplayHomeAsUpEnabled(true)
                    setHomeButtonEnabled(true)
                    // TODO
                    title = ""
                }
            }
        }
    }

    private fun initViews(view: View) {
        val contact = requireArguments().getParcelable(CONTACT_KEY) as Contact?
        view.findViewById<TextView>(R.id.fr_details_name).text = contact?.name
        view.findViewById<TextView>(R.id.fr_details_phone_number).text = contact?.phone
        view.findViewById<TextView>(R.id.fr_details_temperament).text = contact?.temperament?.value
        // TODO
        view.findViewById<TextView>(R.id.fr_details_educationPeriod).text =
            "${contact?.educationPeriod?.start} - ${contact?.educationPeriod?.end}"
        view.findViewById<TextView>(R.id.fr_details_biography).text = contact?.biography
    }

    companion object {
        private const val CONTACT_KEY = "Contact Key"

        @JvmStatic
        fun newInstance(contact: Contact) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(CONTACT_KEY, contact)
            }
        }
    }
}