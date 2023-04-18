package com.example.mercadolibreapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mercadolibreapp.R
import com.example.mercadolibreapp.core.MyApplication
import com.example.mercadolibreapp.data.models.ResponseDTO.*
import com.example.mercadolibreapp.databinding.FragmentSearchBinding
import com.example.mercadolibreapp.domain.usecase.GetProductsBySearchUseCase
import com.example.mercadolibreapp.helpers.Constants.ApiError
import com.example.mercadolibreapp.helpers.Constants.ID_IMAGE_VIEW
import com.example.mercadolibreapp.helpers.Constants.ID_PRODUCT
import com.example.mercadolibreapp.helpers.Constants.QUERY
import com.example.mercadolibreapp.helpers.Either
import com.example.mercadolibreapp.helpers.hide
import com.example.mercadolibreapp.helpers.show
import com.example.mercadolibreapp.presentation.adapter.ProductAdapter
import com.example.mercadolibreapp.presentation.viewmodel.SearchViewModel
import javax.inject.Inject

/**
 * @author Axel Sanchez
 */
class SearchFragment : Fragment() {

    private lateinit var query: String

    @Inject
    lateinit var getProductsBySearchUseCase: GetProductsBySearchUseCase

    private var fragmentSearchBinding: FragmentSearchBinding? = null
    private val binding get() = fragmentSearchBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentSearchBinding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).component.inject(this)
    }

    private val viewModel: SearchViewModel by viewModels(
        factoryProducer = { SearchViewModel.SearchViewModelFactory(getProductsBySearchUseCase) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        query = arguments?.getString(QUERY)?:""

        viewModel.getProduct(query)

        viewModel.getProductLiveData().observe(viewLifecycleOwner) { response ->
            updateView(response)
        }
    }

    private fun updateView(response: Either<ApiError, List<Product?>>?) {
        with(binding) {
            response?.fold(
                left = {
                    tvErrorText.text = it.error
                    cvEmptyState.show()
                    rvProducts.hide()
                }, right = {
                    if ((response as Either.Right).r.isEmpty()) {
                        rvProducts.hide()
                        tvErrorText.text = getString(R.string.there_is_not_products)
                        cvEmptyState.show()
                    } else {
                        rvProducts.show()
                        setAdapter(response.r)
                    }
                }
            )
            cpiLoading.hide()
        }
    }

    private fun setAdapter(products: List<Product?>) {
        val productsAdapter = ProductAdapter(products, itemClick)
        with(binding.rvProducts) {
            layoutManager = LinearLayoutManager(context)
            adapter = productsAdapter
        }
    }

    private val itemClick = { product: Product?, imageView: ImageView ->
        product?.let {
            val bundle = bundleOf(ID_PRODUCT to it.id)
            val extras = FragmentNavigatorExtras(
                imageView to ID_IMAGE_VIEW
            )
            findNavController().navigate(R.id.action_searchFragment_to_detailsFragment, bundle, null, extras)
        }
    }
}