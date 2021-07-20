package com.demin.konturtest.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demin.konturtest.R
import com.demin.konturtest.common.appComponent
import com.demin.konturtest.common.entity.Contact
import com.demin.konturtest.ui.adapters.ContactListAdapter
import com.demin.konturtest.ui.viewmodel.MainFragmentViewModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var factory: MainFragmentViewModel.Factory
    private var mContactDisposable: Disposable? = null

    private lateinit var mContactListView: RecyclerView
    private lateinit var mLoadingView: ProgressBar

    private val viewModel: MainFragmentViewModel by viewModels { factory }
    private val mContactObserver = object : Observer<List<Contact>> {

        override fun onSubscribe(d: Disposable) {
            mContactDisposable = d
            toggleContactListVisibility(false)
        }

        override fun onNext(contacts: List<Contact>) {
            mAdapter.mContactList = contacts
        }

        override fun onError(e: Throwable) {

        }

        override fun onComplete() {
            toggleContactListVisibility(true)
        }

    }
    private val mAdapter = ContactListAdapter {
        openDetailsFragment(it)
    }

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
        mContactListView = view.findViewById(R.id.fr_main_contact_list)
        mContactListView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        mLoadingView = view.findViewById(R.id.fr_main_loading)

        viewModel.loadContacts(mContactObserver)
    }

    override fun onDestroy() {
        mContactDisposable?.dispose()
        super.onDestroy()
    }

    private fun openDetailsFragment(contact: Contact) {
        // TODO: 20.07.2021
        parentFragmentManager.beginTransaction()
            .replace(R.id.ac_main_container, DetailsFragment.newInstance(contact))
            .addToBackStack(DetailsFragment::class.simpleName)
            .commit()
    }

    private fun toggleContactListVisibility(isVisible: Boolean) {
        mContactListView.visibility = if (isVisible) View.VISIBLE else View.GONE
        mLoadingView.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}