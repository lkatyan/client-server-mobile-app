package com.example.clientservermobileapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientservermobileapp.R
import com.example.clientservermobileapp.databinding.FragmentMainBinding
import com.example.clientservermobileapp.ui.adapters.NewsAdapter
import com.example.clientservermobileapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        newsAdapter.setOnItemClickListener {
            val bundle = bundleOf("article" to it)
            view.findNavController().navigate(
                R.id.action_mainFragment_to_detailsFragment,
                bundle
            )
        }

        viewModel.newsLiveData.observe(viewLifecycleOwner) { res ->
            when(res) {
                is Resource.Success -> {
                    progress_bar.visibility = View.INVISIBLE
                    res.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
                    progress_bar.visibility = View.INVISIBLE
                    res.data?.let {
                        Log.e("checkData", "MainFragment: error: ${it}")
                    }
                }
                is Resource.Loading -> {
                    progress_bar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        recycler.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}