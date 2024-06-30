package com.example.moodup.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moodup.R
import com.example.moodup.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var comicsRecyclerView: RecyclerView
    private lateinit var comicsAdapter: ComicsAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = view.findViewById(R.id.progressBar)

        comicsRecyclerView = view.findViewById(R.id.comicsRecyclerView)
        comicsRecyclerView.layoutManager = LinearLayoutManager(context)

        comicsAdapter = ComicsAdapter(emptyList()) {
            homeViewModel.loadMoreComics()
        }
        comicsRecyclerView.adapter = comicsAdapter

        homeViewModel.comics.observe(viewLifecycleOwner, Observer { comics ->
            comicsAdapter.updateComics(comics)
        })

        homeViewModel.loading.observe(viewLifecycleOwner, Observer { it ->
            progressBar.visibility = if(it) View.VISIBLE else View.GONE
        })
    }
}
