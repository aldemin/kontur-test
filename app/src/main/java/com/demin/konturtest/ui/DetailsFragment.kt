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

    private fun initToolbar(view:View) {
        activity?.let { activity ->
            activity.apply {
                setActionBar(view.findViewById(R.id.fr_details_toolbar))
                actionBar?.let {
                    it.setDisplayHomeAsUpEnabled(true)
                    it.setHomeButtonEnabled(true)
                    // TODO
                    it.title = ""
                }
            }
        }
    }

    // TODO: temporary solution for skeleton testing. will be removed
    private fun initViews(view:View) {
        view.findViewById<TextView>(R.id.fr_details_name).text = "Aleksande Demin"
        view.findViewById<TextView>(R.id.fr_details_phone_number).text = "+123456789"
        view.findViewById<TextView>(R.id.fr_details_temperament).text = "phlegmatic"
        view.findViewById<TextView>(R.id.fr_details_educationPeriod).text = "04.08.1993-15.07.2021"
        view.findViewById<TextView>(R.id.fr_details_biography).text = "This is my humble biography"
    }

    companion object {
        @JvmStatic
        fun newInstance() = DetailsFragment()
    }
}