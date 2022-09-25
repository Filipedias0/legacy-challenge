package com.picpay.desafio.android.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.databinding.FragmentContactListBinding
import com.picpay.desafio.android.presentation.viewModels.MainViewModel
import com.picpay.desafio.android.util.UserListAdapter
import com.picpay.desafio.android.util.collectLatestLifecycleFlow
import org.koin.android.ext.android.inject

class ContactListFragment : Fragment() {
    private lateinit var adapter: UserListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val viewModel by inject<MainViewModel>()
    private lateinit var binding: FragmentContactListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(binding)
        subscribeToObservers()
        viewModel.getUsers()
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        adapter = UserListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
    }
    private fun bindView(binding: FragmentContactListBinding){
        recyclerView = binding.recyclerView
        progressBar = binding.userListProgressBar
    }

    private fun subscribeToObservers(){
        collectLatestLifecycleFlow(viewModel.loadingStateFlow) { loadingState ->
            progressBar.visibility = loadingState
        }

        collectLatestLifecycleFlow(viewModel.userListStateFlow) { userList ->
            adapter.users = userList
        }
    }
}