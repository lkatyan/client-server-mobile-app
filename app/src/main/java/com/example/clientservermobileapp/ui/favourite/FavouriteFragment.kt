package com.example.clientservermobileapp.ui.favourite

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
import com.example.clientservermobileapp.databinding.FragmentFavouriteBinding
import com.example.clientservermobileapp.ui.adapters.NewsAdapter
import com.example.clientservermobileapp.ui.search.SearchViewModel
import com.example.clientservermobileapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favourite.*
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_search.*

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private var _binding: FragmentFavouriteBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<FavouriteViewModel>()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouriteBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        newsAdapter.setOnItemClickListener {
            val bundle = bundleOf("article" to it)
            view.findNavController().navigate(
                R.id.action_favouriteFragment_to_detailsFragment,
                bundle
            )
        }

        viewModel.favLiveData.observe(viewLifecycleOwner) { res ->
            res.let {
                newsAdapter.differ.submitList(it)
            }
        }
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        recycler_fav.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}