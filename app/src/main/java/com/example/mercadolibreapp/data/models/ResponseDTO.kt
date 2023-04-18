package com.example.mercadolibreapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * @author Axel Sanchez
 * */
@Entity data class ResponseDTO(
    var available_filters: List<AvailableFilter?>? = null,
    var available_sorts: List<AvailableSort?>? = null,
    var filters: List<Filter?>? = null,
    var paging: Paging? = null,
    var query: String? = null,
    var related_Productos: List<Any?>? = null,
    var results: List<Product?>? = null,
    var secondary_Productos: List<Any?>? = null,
    var site_id: String? = null,
    var sort: Sort? = null
) {
    @Entity data class AvailableFilter(
        var id: String? = null,
        var name: String? = null,
        var type: String? = null,
        var values: List<Value?>? = null
    ) {
        @Entity data class Value(
            var id: String? = null,
            var name: String? = null,
            var Productos: Int? = null
        )
    }

    @Entity data class AvailableSort(
        var id: String? = null,
        var name: String? = null
    )

    @Entity data class Filter(
        var id: String? = null,
        var name: String? = null,
        var type: String? = null,
        var values: List<Value?>? = null
    ) {
        @Entity data class Value(
            var id: String? = null,
            var name: String? = null,
            var path_from_root: List<PathFromRoot?>? = null
        ) {
            @Entity data class PathFromRoot(
                var id: String? = null,
                var name: String? = null
            )
        }
    }

    @Entity data class Paging(
        var limit: Int? = null,
        var offset: Int? = null,
        var primary_Productos: Int? = null,
        var total: Int? = null
    )

    @Entity data class Product(
        @PrimaryKey var id: String,
        @SerializedName("accepts_mercadopago")
        var acceptsMercadoPago: Boolean? = null,
        var address: Address? = null,
        var attributes: List<Attribute?>? = null,
        @SerializedName("available_quantity")
        var availableQuantity: Int? = null,
        @SerializedName("buying_mode")
        var buyingMode: String? = null,
        @SerializedName("catalog_product_id")
        var catalogProductId: String? = null,
        @SerializedName("category_id")
        var categoryId: String? = null,
        var condition: String? = null,
        @SerializedName("currency_id")
        var currencyId: String? = null,
        @SerializedName("differential_pricing")
        var differentialPricing: DifferentialPricing? = null,
        @SerializedName("domain_id")
        var domainId: String? = null,
        var installments: Installments? = null,
        @SerializedName("listing_type_id")
        var listingTypeId: String? = null,
        @SerializedName("official_store_id")
        var officialStoreId: Int? = null,
        @SerializedName("original_price")
        var originalPrice: Number? = null,
        var permalink: String? = null,
        var price: Number? = null,
        var seller: Seller? = null,
        @SerializedName("seller_address")
        var sellerAddress: SellerAddress? = null,
        var shipping: Shipping? = null,
        @SerializedName("site_id")
        var siteId: String? = null,
        @SerializedName("sold_quantity")
        var soldQuantity: Int? = null,
        @SerializedName("stop_time")
        var stopTime: String? = null,
        var tags: List<String?>? = null,
        var thumbnail: String? = null,
        var title: String? = null,
        var search: String? = null
    ) {
        @Entity data class Address(
            var city_id: String? = null,
            var city_name: String? = null,
            var state_id: String? = null,
            var state_name: String? = null
        )

        @Entity data class Attribute(
            var attribute_group_id: String? = null,
            var attribute_group_name: String? = null,
            var id: String? = null,
            var name: String? = null,
            var source: Long? = null,
            var value_id: String? = null,
            var value_name: String? = null,
            var value_struct: ValueStruct? = null,
            var values: List<Value?>? = null
        ) {

            @Entity data class ValueStruct(
                var number: Float? = 0.0f,
                var unit: String? = null
            )

            @Entity data class Value(
                var id: String? = null,
                var name: String? = null,
                var source: Long? = null,
                var struct: ValueStruct? = null
            )
        }

        @Entity data class DifferentialPricing(
            var id: Int? = null
        )

        @Entity data class Installments(
            var amount: Double? = null,
            var currency_id: String? = null,
            var quantity: Int? = null,
            var rate: Double? = null
        )

        @Entity data class Seller(
            var car_dealer: Boolean? = null,
            var eshop: Eshop? = null,
            var id: Int? = null,
            var permalink: String? = null,
            var real_estate_agency: Boolean? = null,
            var registration_date: String? = null,
            var seller_reputation: SellerReputation? = null,
            var tags: List<String?>? = null
        ) {
            @Entity data class Eshop(
                var eshop_experience: Int? = null,
                var eshop_id: Int? = null,
                var eshop_locations: List<EshopLocation?>? = null,
                var eshop_logo_url: String? = null,
                var eshop_rubro: EShopRubro? = null,
                var eshop_status_id: Int? = null,
                var nick_name: String? = null,
                var seller: Int? = null,
                var site_id: String? = null
            ) {

                @Entity data class EShopRubro(
                    var id: String? = null,
                    var name: String? = null,
                    var category_id: String? = null
                )

                @Entity data class EshopLocation(
                    var city: City? = null,
                    var country: Country? = null,
                    var neighborhood: Neighborhood? = null,
                    var state: State? = null
                ) {
                    @Entity data class City(
                        var id: String? = null
                    )

                    @Entity data class Country(
                        var id: String? = null
                    )

                    @Entity data class Neighborhood(
                        var id: String? = null
                    )

                    @Entity data class State(
                        var id: String? = null
                    )
                }
            }

            @Entity data class SellerReputation(
                var level_id: String? = null,
                var metrics: Metrics? = null,
                var power_seller_status: String? = null,
                var transactions: Transactions? = null
            ) {
                @Entity data class Metrics(
                    var cancellations: Cancellations? = null,
                    var claims: Claims? = null,
                    var delayed_handling_time: DelayedHandlingTime? = null,
                    var sales: Sales? = null
                ) {
                    @Entity data class Cancellations(
                        var period: String? = null,
                        var rate: Double? = null,
                        var value: Int? = null
                    )

                    @Entity data class Claims(
                        var period: String? = null,
                        var rate: Double? = null,
                        var value: Int? = null
                    )

                    @Entity data class DelayedHandlingTime(
                        var period: String? = null,
                        var rate: Double? = null,
                        var value: Int? = null
                    )

                    @Entity data class Sales(
                        var completed: Int? = null,
                        var period: String? = null
                    )
                }

                @Entity data class Transactions(
                    var canceled: Int? = null,
                    var completed: Int? = null,
                    var period: String? = null,
                    var ratings: Ratings? = null,
                    var total: Int? = null
                ) {
                    @Entity data class Ratings(
                        var negative: Double? = null,
                        var neutral: Double? = null,
                        var positive: Double? = null
                    )
                }
            }
        }

        @Entity data class SellerAddress(
            var address_line: String? = null,
            var city: City? = null,
            var comment: String? = null,
            var country: Country? = null,
            var id: String? = null,
            var latitude: String? = null,
            var longitude: String? = null,
            var state: State? = null,
            var zip_code: String? = null
        ) {
            @Entity data class City(
                var id: String? = null,
                var name: String? = null
            )

            @Entity data class Country(
                var id: String? = null,
                var name: String? = null
            )

            @Entity data class State(
                var id: String? = null,
                var name: String? = null
            )
        }

        @Entity data class Shipping(
            @SerializedName("free_shipping")
            var freeShipping: Boolean? = null,
            var logistic_type: String? = null,
            var mode: String? = null,
            var store_pick_up: Boolean? = null,
            var tags: List<String?>? = null
        )
    }

    @Entity data class Sort(
        var id: String? = null,
        var name: String? = null
    )
}