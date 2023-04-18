package com.example.mercadolibreapp.presentation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.mercadolibreapp.core.MyApplication
import com.example.mercadolibreapp.data.models.ProductDetails
import com.example.mercadolibreapp.data.models.ResponseDTO
import com.example.mercadolibreapp.databinding.FragmentDetailsBinding
import com.example.mercadolibreapp.domain.usecase.GetProductDetailsUseCase
import com.example.mercadolibreapp.helpers.*
import com.example.mercadolibreapp.helpers.Constants.MERCADO_LIBRE_PACKAGE
import com.example.mercadolibreapp.presentation.adapter.ImageAdapter
import com.example.mercadolibreapp.presentation.viewmodel.DetailsViewModel
import javax.inject.Inject

/**
 * @author Axel Sanchez
 */
class DetailsFragment : Fragment() {

    private lateinit var idProduct: String

    @Inject
    lateinit var getProductDetailsUseCase: GetProductDetailsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).component.inject(this)
    }

    private val viewModel: DetailsViewModel by viewModels(
        factoryProducer = { DetailsViewModel.DetailsViewModelFactory(getProductDetailsUseCase) }
    )

    private var fragmentDetailsBinding: FragmentDetailsBinding? = null
    private val binding get() = fragmentDetailsBinding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentDetailsBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        idProduct = arguments?.getString(Constants.ID_PRODUCT) ?: ""

        viewModel.getProduct(idProduct)

        viewModel.getProductLiveData().observe(viewLifecycleOwner) { product ->
            updateView(product)
        }
    }

    private fun updateView(response: Either<Constants.ApiError, ProductDetails?>?) {

        with(binding) {
            response?.fold(
                left = {
                    tvErrorText.text = it.error
                    cvEmptyState.show()
                    clContent.hide()
                }, right = {
                    clContent.show()

                    it?.let { product ->
                        product.title?.let { title ->
                            tvTitle.text = title
                        } ?: tvTitle.hide()

                        vpImages.adapter = ImageAdapter(it.pictures)
                        diIndicator.attachTo(vpImages)

                        product.availableQuantity?.let { availableQuatity ->
                            if (availableQuatity > 0) tvAvailableQuantity.text = "Unidades disponibles: $availableQuatity"
                        } ?: tvAvailableQuantity.hide()

                        product.price?.let { price ->
                            tvPrice.text = "$${price.toFloat()}"
                        } ?: tvPrice.hide()

                        product.shipping?.freeShipping
                            ?.let { freeShipping ->
                                if (freeShipping) tvFreeShipping.show()
                                else tvFreeShipping.hide()
                            } ?: tvFreeShipping.hide()

                        product.description?.let { description ->
                            tvDesacription.text = description.text
                            tvDesacription.show()
                        }?: kotlin.run { tvDesacription.hide() }

                        btnBuy.setOnClickListener {
                            openMercadoLibreApp(product)
                        }
                    }
                }
            )
            cpiLoading.hide()
        }
    }

    private fun openMercadoLibreApp(product: ProductDetails){
        val uri = Uri.parse(product.permalink)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage(MERCADO_LIBRE_PACKAGE)

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(product.permalink)
                )
            )
        }
    }
}