package com.picpay.desafio.android.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.ui.userAdapter.UserListAdapter
import com.picpay.desafio.android.util.collectLatestLifecycleFlow
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter
    private val viewModel: MainViewModel by viewModel()
    //IMPLEMENTAR VIEW BINDING
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUsers()

        collectLatestLifecycleFlow(viewModel.loadingStateFlow) { loadingState ->
            progressBar.visibility = loadingState
        }

        collectLatestLifecycleFlow(viewModel.userListStateFlow) { userList ->
            adapter.users = userList
        }


        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.user_list_progress_bar)
        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        progressBar.visibility = View.VISIBLE
    }
}
