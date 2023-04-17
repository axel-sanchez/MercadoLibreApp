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
import com.example.mercadolibreapp.data.models.ResponseDTO.Product
import com.example.mercadolibreapp.databinding.FragmentDetailsBinding
import com.example.mercadolibreapp.domain.usecase.GetProductUseCase
import com.example.mercadolibreapp.helpers.Constants
import com.example.mercadolibreapp.helpers.Constants.MERCADO_LIBRE_PACKAGE
import com.example.mercadolibreapp.helpers.hide
import com.example.mercadolibreapp.helpers.load
import com.example.mercadolibreapp.helpers.show
import com.example.mercadolibreapp.presentation.viewmodel.DetailsViewModel
import javax.inject.Inject

/**
 * @author Axel Sanchez
 */
class DetailsFragment : Fragment() {

    private lateinit var idProduct: String

    @Inject
    lateinit var getProductUseCase: GetProductUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApplication).component.inject(this)
    }

    private val viewModel: DetailsViewModel by viewModels(
        factoryProducer = { DetailsViewModel.DetailsViewModelFactory(getProductUseCase) }
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

    fun updateView(product: Product?) {
        with(binding) {
            product?.let {
                it.title?.let { title ->
                    tvTitle.text = title
                } ?: tvTitle.hide()

                it.thumbnail?.let { urlImage ->
                    ivImage.load(urlImage)
                } ?: ivImage.hide()

                it.available_quantity?.let { availableQuatity ->
                    if (availableQuatity > 0) tvAvailableQuantity.text = "Unidades disponibles: $availableQuatity"
                } ?: tvAvailableQuantity.hide()

                it.price?.let { price ->
                    tvPrice.text = "$${price.toFloat()}"
                } ?: tvPrice.hide()

                it.shipping?.free_shipping
                    ?.let { freeShipping ->
                        if (freeShipping) tvFreeShipping.show()
                        else tvFreeShipping.hide()
                    } ?: tvFreeShipping.hide()

                it.original_price?.let { originalPrice ->
                    if (originalPrice.toFloat() > 0.0f) {
                        tvOriginalPrice.text = "$${originalPrice}"
                        tvOriginalPrice.paintFlags = tvOriginalPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    } else tvOriginalPrice.hide()
                } ?: tvOriginalPrice.hide()

                it.seller?.let { seller ->

                    seller.seller_reputation?.transactions?.completed
                        ?.let { completed ->
                            tvSoldProducts.text = "$completed ventas"
                        } ?: tvSoldProducts.hide()

                    seller.eshop?.let { eshop ->
                        eshop.nick_name?.let { name ->
                            tvNameSeller.text = name
                        } ?: tvNameSeller.hide()

                        eshop.eshop_logo_url?.let { logoUrl ->
                            Glide.with(requireContext())
                                .load(logoUrl)
                                .into(ivLogoSeller)
                        } ?: ivLogoSeller.hide()
                    } ?: kotlin.run {
                        tvNameSeller.hide()
                        ivLogoSeller.hide()
                    }
                } ?: kotlin.run {
                    tvSoldProducts.hide()
                    tvNameSeller.hide()
                    ivLogoSeller.hide()
                }

                it.seller_address?.let { address ->
                    var addressSeller = ""
                    address.city?.name
                        ?.let { name -> addressSeller += name }
                    address.state?.name
                        ?.let { name -> addressSeller += ", $name" }
                    address.country?.name
                        ?.let { name -> addressSeller += ", $name" }
                    tvAddressSeller.text = addressSeller
                } ?: tvAddressSeller.hide()

                btnBuy.setOnClickListener {
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
        }
    }
}