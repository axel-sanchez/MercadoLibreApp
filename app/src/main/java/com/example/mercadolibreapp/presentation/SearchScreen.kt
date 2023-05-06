package com.example.mercadolibreapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.mercadolibreapp.R
import com.example.mercadolibreapp.data.models.ResponseDTO
import com.example.mercadolibreapp.helpers.Constants
import com.example.mercadolibreapp.helpers.Either
import com.example.mercadolibreapp.presentation.viewmodel.SearchViewModel

/**
 * @author Axel Sanchez
 */

@Composable
fun SearchScreen(
    query: String, viewModel: SearchViewModel
) {
    val products: Either<Constants.ApiError, List<ResponseDTO.Product?>> by viewModel.getProductLiveData().observeAsState(initial = Either.Right(
        listOf()
    ))
    
    /*val showLoading = rememberSaveable(inputs = , stateSaver = ) {

    }*/

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.colorPrimary))
    ) {
        val (emptyState, loading) = createRefs()

        EmptyState(query = query, modifier = Modifier.constrainAs(emptyState) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        Loading(modifier = Modifier.constrainAs(loading){
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
    }
}

@Composable
fun Loading(modifier: Modifier){
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyState(query: String, modifier: Modifier) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .padding(12.dp),
        elevation = 4.dp
    ) {
        Text(
            text = "No se encontraron productos vinculados a su búsqueda: $query",
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            softWrap = true,
            style = TextStyle(fontSize = 18.sp)
        )
    }
}