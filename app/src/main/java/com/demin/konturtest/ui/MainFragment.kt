package com.demin.konturtest.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.demin.konturtest.R
import com.demin.konturtest.common.appComponent
import com.demin.konturtest.common.entity.Contact
import com.demin.konturtest.ui.adapters.ContactListAdapter
import com.demin.konturtest.ui.viewmodel.MainFragmentViewModel
import com.demin.konturtest.ui.viewmodel.MainFragmentViewState
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var factory: MainFragmentViewModel.Factory

    private lateinit var mContactListView: RecyclerView
    private lateinit var mContactListContainerView: SwipeRefreshLayout
    private lateinit var mLoadingView: ProgressBar
    private lateinit var mToolbarSearch: SearchView

    private val viewModel: MainFragmentViewModel by viewModels { factory }

    private val mAdapter = ContactListAdapter {
        openDetailsFragment(it)
    }

    private val mViewStateObserver = Observer<MainFragmentViewState> {
        render(it)
    }

    private val mRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.updateContactList()
    }

    private val mQueryTextListener = object : OnQueryTextListener {
        override fun onQueryTextSubmit(value: String?) = true

        override fun onQueryTextChange(value: String?): Boolean {
            Log.d(TAG, "onQueryTextChange: $value")
            viewModel.searchContact(value ?: "")
            return true
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach")
        context.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        viewModel.onCreateFragment(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: $savedInstanceState")
        mContactListView = view.findViewById(R.id.fr_main_contact_list)
        mContactListView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        mContactListContainerView = view.findViewById(R.id.fr_main_contact_list_container)
        mContactListContainerView.setOnRefreshListener(mRefreshListener)
        mLoadingView = view.findViewById(R.id.fr_main_loading)
        mToolbarSearch = view.findViewById(R.id.fr_main_toolbar_search)
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
        viewModel.viewState().observe(viewLifecycleOwner, mViewStateObserver)
        mToolbarSearch.setOnQueryTextListener(mQueryTextListener)
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        viewModel.viewState().removeObserver(mViewStateObserver)
        mToolbarSearch.setOnQueryTextListener(null)
        super.onPause()
    }

    private fun openDetailsFragment(contact: Contact) {
        Log.d(TAG, "openDetailsFragment")
        parentFragmentManager.beginTransaction()
            .replace(R.id.ac_main_container, DetailsFragment.newInstance(contact))
            .addToBackStack(DetailsFragment::class.simpleName)
            .commit()
    }

    private fun render(viewState: MainFragmentViewState) {
        Log.d(TAG, "render")
        mContactListContainerView.visibility = if (viewState.isLoading) View.GONE else View.VISIBLE
        mLoadingView.visibility = if (viewState.isLoading) View.VISIBLE else View.GONE
        mAdapter.mContactList = viewState.contactList
        mContactListContainerView.isRefreshing = viewState.isRefreshingViewShowing
        if (viewState.isErrorShow) {
            Snackbar.make(requireView(), viewState.errorMessage, Snackbar.LENGTH_LONG).show()
        }
    }

    companion object {

        private val TAG = MainFragment::class.simpleName

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}