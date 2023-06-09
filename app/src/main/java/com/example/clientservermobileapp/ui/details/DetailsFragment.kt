package com.example.clientservermobileapp.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.clientservermobileapp.R
import com.example.clientservermobileapp.databinding.FragmentDetailsBinding
import com.example.clientservermobileapp.ui.favourite.FavouriteFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val mBinding get() = _binding!!

    private val bundleArgs: DetailsFragmentArgs by navArgs()
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleArg = bundleArgs.article

        articleArg.let { article ->
            article.urlToImage.let {
                Glide.with(this)
                    .load("https://media.cnn.com/api/v1/images/stellar/prod/230324121930-01-planetary-alignment-night-sky-2020.jpg")
                    .into(mBinding.headerImage)
            }
            mBinding.headerImage.clipToOutline = true
            mBinding.articleDetailsTitle.text = article.title
            mBinding.articleDetailsDescriptionText.text = article.description

            mBinding.articleDetailsButton.setOnClickListener {
                try {
                    Intent()
                        .setAction(Intent.ACTION_VIEW)
                        .addCategory(Intent.CATEGORY_BROWSABLE)
                        .setData(Uri.parse(takeIf { URLUtil.isValidUrl(article.url) }
                            ?.let {
                                article.url
                            } ?: "https://google.com" ))
                            .let {
                                ContextCompat.startActivity(requireContext(), it, null)
                            }
                } catch (e: Exception) {
                    Toast.makeText(context, "The device doesn't have any browser to view the document", Toast.LENGTH_SHORT)
                }
            }

            mBinding.iconFavourite.setOnClickListener {
                viewModel.saveFavouriteArticle(article)
            }

            mBinding.iconBack.setOnClickListener {
                this.activity?.onBackPressed()
            }

            mBinding.iconShare.setOnClickListener {
                viewModel.deleteFavouriteArticle(article)
                this.activity?.onBackPressed()
            }
        }
    }
}