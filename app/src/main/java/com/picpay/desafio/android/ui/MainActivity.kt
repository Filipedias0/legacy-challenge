package com.picpay.desafio.android.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.ui.userAdapter.UserListAdapter
import com.picpay.desafio.android.util.collectLatestLifecycleFlow
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var adapter: UserListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        bindView(binding)

        setupRecyclerView()
        subscribeToFlow()
        viewModel.getUsers()
    }

    private fun setupRecyclerView(){
        adapter = UserListAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
    private fun bindView(binding: ActivityMainBinding){
        recyclerView = binding.recyclerView
        progressBar = binding.userListProgressBar
    }

    private fun subscribeToFlow(){
        collectLatestLifecycleFlow(viewModel.loadingStateFlow) { loadingState ->
            progressBar.visibility = loadingState
        }

        collectLatestLifecycleFlow(viewModel.userListStateFlow) { userList ->
            adapter.users = userList
        }
    }
}
