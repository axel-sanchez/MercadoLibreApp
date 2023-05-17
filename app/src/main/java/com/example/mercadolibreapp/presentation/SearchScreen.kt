package com.example.mercadolibreapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.example.mercadolibreapp.R
import com.example.mercadolibreapp.data.models.DataProducts
import com.example.mercadolibreapp.helpers.Constants
import com.example.mercadolibreapp.presentation.viewmodel.SearchViewModel

/**
 * @author Axel Sanchez
 */

@Composable
fun SearchScreen(
    query: String, viewModel: SearchViewModel, navigateDetailsScreen: (String) -> Unit
) {
    viewModel.getProducts(query)
    val dataProducts: DataProducts by viewModel.getProductsLiveData()
        .observeAsState(initial = DataProducts())


    DisposableEffect(dataProducts) {
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
        }, dataProducts)

        Loading(modifier = Modifier.constrainAs(loading) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, dataProducts)

        ProductList(dataProducts, viewModel, navigateDetailsScreen)
    }
}

@Composable
private fun Loading(modifier: Modifier, dataProducts: DataProducts) {
    if (dataProducts.products == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun ErrorState(modifier: Modifier, dataProducts: DataProducts) {
    dataProducts.products?.let { products ->
        if (products.isEmpty()) {
            ErrorCard(Constants.ApiError.EMPTY_PRODUCTS.error, modifier)
        }
    } ?: run {
        dataProducts.apiError?.let {
            ErrorCard(it.error, modifier)
        }
    }
}

@Composable
fun ProductList(dataProducts: DataProducts, viewModel: SearchViewModel, navigateDetailsScreen: (String) -> Unit) {
    if (!dataProducts.products.isNullOrEmpty()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(dataProducts.products) { index, product ->
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navigateDetailsScreen(product?.id ?: "")
                        }) {
                    val (card, title, price, freeShipping, divider) = createRefs()
                    Card(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .height(150.dp)
                            .width(150.dp)
                            .padding(10.dp)
                            .constrainAs(card) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(title.start)
                            }
                    ) {
                        Image(
                            painter = rememberImagePainter(
                                data = product?.thumbnail
                            ),
                            contentDescription = "imagen del producto en el item"
                        )
                    }
                    Text(
                        modifier = Modifier
                            .constrainAs(title) {
                                top.linkTo(card.top)
                                start.linkTo(card.end)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .padding(top = 10.dp, end = 10.dp),
                        text = product?.title ?: "",
                        softWrap = true
                    )
                    Text(
                        modifier = Modifier
                            .constrainAs(price) {
                                top.linkTo(title.bottom)
                                start.linkTo(card.end)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                            .padding(top = 10.dp, end = 10.dp),
                        text = product?.price.toString(),
                        softWrap = true
                    )
                    product?.shipping?.freeShipping?.let { isFreeShipping ->
                        if (isFreeShipping) {
                            Text(
                                modifier = Modifier
                                    .constrainAs(freeShipping) {
                                        top.linkTo(price.bottom)
                                        start.linkTo(card.end)
                                        end.linkTo(parent.end)
                                        width = Dimension.fillToConstraints
                                    }
                                    .padding(top = 10.dp, end = 10.dp, bottom = 10.dp),
                                text = stringResource(R.string.envio_gratis),
                                softWrap = true, color = Color.Green
                            )
                        }
                    }
                    if (index != dataProducts.products.size - 1) {
                        Divider(
                            modifier = Modifier.constrainAs(divider) {
                                top.linkTo(card.bottom)
                            },
                            color = Color.Gray,
                            thickness = 1.dp
                        )
                    }
                }
            }
        }
    }
}