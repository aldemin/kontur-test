package com.demin.konturtest.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.demin.konturtest.R
import com.demin.konturtest.common.appComponent
import com.demin.konturtest.ui.viewmodel.MainFragmentViewModel
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var factory: MainFragmentViewModel.Factory

    private val viewModel: MainFragmentViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: temporary solution for skeleton testing. will be removed
        view.findViewById<ProgressBar>(R.id.fr_main_loading).apply {
            visibility = View.VISIBLE
            setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.ac_main_container, DetailsFragment.newInstance())
                    .addToBackStack(DetailsFragment::class.simpleName)
                    .commit()
            }
        }
        view.findViewById<RecyclerView>(R.id.fr_main_contact_list).apply {
            visibility = View.GONE
        }
        // TODO: end of todo
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}