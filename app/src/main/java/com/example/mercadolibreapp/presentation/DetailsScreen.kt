package com.example.mercadolibreapp.presentation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.example.mercadolibreapp.R
import com.example.mercadolibreapp.data.models.ProductDetails
import com.example.mercadolibreapp.data.repository.FakeRepository
import com.example.mercadolibreapp.helpers.Constants
import com.example.mercadolibreapp.presentation.viewmodel.DetailsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

/**
 * @author Axel Sanchez
 */
@Composable
fun DetailsScreen(idProduct: String, viewModel: DetailsViewModel) {
    viewModel.getProduct(idProduct)
    val productDetails: ProductDetails by viewModel.getProductLiveData()
        .observeAsState(initial = ProductDetails(""))

    DisposableEffect(productDetails) {
        onDispose {
            viewModel.reset()
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (emptyState, loading) = createRefs()

        ErrorState(modifier = Modifier.constrainAs(emptyState) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, productDetails)

        Loading(modifier = Modifier.constrainAs(loading) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, productDetails)

        ContentDetails(productDetails)
    }
}

@Composable
private fun Loading(modifier: Modifier, productDetails: ProductDetails) {
    if (productDetails.title == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun ErrorState(modifier: Modifier, productDetails: ProductDetails) {
    productDetails.apiError?.let { apiError ->
        ErrorCard(apiError.error, modifier)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ContentDetails(productDetails: ProductDetails) {
    if (productDetails.title != null) {

        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Text(
                text = productDetails.title,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            productDetails.pictures?.let { pictures ->
                val pagerState = rememberPagerState()
                HorizontalPager(
                    state = pagerState,
                    count = pictures.size,
                    modifier = Modifier
                        .height(250.dp)
                        .width(250.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Image(

                        painter = rememberImagePainter(
                            data = pictures[it]?.secure_url
                        ),
                        contentDescription = "imagen del producto en la descripcion"
                    )
                }
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            Text(
                text = "${stringResource(R.string.available_quantity)}${productDetails.availableQuantity}",
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                text = "$${productDetails.price}", modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(), textAlign = TextAlign.Center
            )

            if (productDetails.shipping?.freeShipping == true) {
                Text(
                    text = stringResource(R.string.free_shipping), modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(), textAlign = TextAlign.Center
                )
            }

            Text(
                text = "$${productDetails.description}", modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(), textAlign = TextAlign.Center
            )
            val context = LocalContext.current
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    val uri = Uri.parse(productDetails.permalink)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setPackage(Constants.MERCADO_LIBRE_PACKAGE)

                    try {
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(productDetails.permalink)
                            )
                        )
                    }
                }) {
                Text(text = "Comprar producto")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyPreviewProductDetails(){
    val repo = FakeRepository()
    ContentDetails(repo.productDetails)
}
