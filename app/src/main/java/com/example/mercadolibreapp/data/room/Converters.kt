package com.example.mercadolibreapp.data.room

import androidx.room.TypeConverter
import com.example.mercadolibreapp.data.models.Description
import com.example.mercadolibreapp.data.models.Picture
import com.example.mercadolibreapp.data.models.ProductDetails
import com.example.mercadolibreapp.data.models.ResponseDTO.Product.*
import com.google.gson.Gson

private const val nullStr = "null"
/**
 * @author Axel Sanchez
 */
class Converters{
    private val gson: Gson = Gson()

    @TypeConverter
    fun fromProductDetails(productDetails: ProductDetails?): String? {
        productDetails?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toProductDetails(productDetails: String?): ProductDetails? {
        productDetails?.let {
            return gson.fromJson(it, ProductDetails::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromPicture(picture: Picture?): String? {
        picture?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toPicture(picture: String?): Picture? {
        picture?.let {
            return gson.fromJson(it, Picture::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromPictureList(list: List<Picture?>?): String? {
        var response = ""
        list?.let {
            for (i in list.indices) {
                response += if (i == 0) fromPicture(it[i])
                else ";${fromPicture(it[i])}"
            }
        } ?: return null
        return response
    }

    @TypeConverter
    fun toPictureList(concat: String?): List<Picture?>? {
        val list = concat?.split(";")
        list?.let {
            return it.map { str -> toPicture(str) }
        } ?: return null
    }

    @TypeConverter
    fun froDescription(description: Description?): String? {
        description?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toDescription(description: String?): Description? {
        description?.let {
            return gson.fromJson(it, Description::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromAddress(address: Address?): String? {
        address?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toAddress(address: String?): Address? {
        address?.let {
            return gson.fromJson(it, Address::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromDifferentialPricing(differentialPricing: DifferentialPricing?): Int? {
        return differentialPricing?.id
    }

    @TypeConverter
    fun toDifferentialPricing(id: Int?): DifferentialPricing? {
        return DifferentialPricing(id)
    }

    @TypeConverter
    fun fromInstallments(installments: Installments?): String? {
        installments?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toInstallments(installments: String?): Installments? {
        installments?.let {
            return gson.fromJson(it, Installments::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromOriginalPrice(number: Number?): Int? {
        return number?.toInt()
    }

    @TypeConverter
    fun toOriginalPrice(number: Int?): Number? {
        return number
    }

    @TypeConverter
    fun fromTags(tags: List<String?>?): String? {
        var response = ""
        tags?.let {
            for (i in tags.indices) {
                response += if (i == 0) tags[i]
                else ";${tags[i]}"
            }
        } ?: return null
        return response
    }

    @TypeConverter
    fun toTags(concat: String?): List<String?>? {
        val list = concat?.split(";")
        list?.let {
            return it.map { str -> if (str != nullStr) str else null }
        } ?: return null
    }

    @TypeConverter
    fun fromShipping(shipping: Shipping?): String? {
        shipping?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toShipping(string: String?): Shipping? {
        string?.let {
            return gson.fromJson(it, Shipping::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromCity(city: SellerAddress.City?): String? {
        city?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toCity(string: String?): SellerAddress.City? {
        string?.let {
            return gson.fromJson(it, SellerAddress.City::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromCountry(country: SellerAddress.Country?): String? {
        country?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toCountry(string: String?): SellerAddress.Country? {
        string?.let {
            return gson.fromJson(it, SellerAddress.Country::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromState(state: SellerAddress.State?): String? {
        state?.let { return gson.toJson(it) } ?: return null
    }

    @TypeConverter
    fun toState(string: String?): SellerAddress.State? {
        string?.let {
            return gson.fromJson(it, SellerAddress.State::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromAttribute(attribute: Attribute?): String? {
        attribute?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toAttribute(str: String?): Attribute? {
        str?.let {
            return gson.fromJson(it, Attribute::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromValueStruct(valueStruct: Attribute.ValueStruct?): String? {
        valueStruct?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toValueStruct(str: String?): Attribute.ValueStruct? {
        str?.let {
            return gson.fromJson(it, Attribute.ValueStruct::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromAttributeList(list: List<Attribute?>?): String? {
        var response = ""
        list?.let {
            for (i in list.indices) {
                response += if (i == 0) fromAttribute(it[i])
                else ";${fromAttribute(it[i])}"
            }
        } ?: return null
        return response
    }

    @TypeConverter
    fun toAttributeList(concat: String?): List<Attribute?>? {
        val newList = concat?.split(";")
        newList?.let {
            return it.map { str -> toAttribute(str) }
        } ?: return null
    }

    @TypeConverter
    fun fromValueList(list: List<Attribute.Value?>?): String? {
        var response = ""
        list?.let {
            for (i in list.indices) {
                response += if (i == 0) fromValue(list[i])
                else ";${fromValue(list[i])}"
            }
        } ?: return null
        return response
    }

    @TypeConverter
    fun toValueList(concat: String?): List<Attribute.Value?>? {
        val newList = concat?.split(";")
        newList?.let {
            return it.map { str -> toValue(str) }
        } ?: return null
    }

    @TypeConverter
    fun fromValue(value: Attribute.Value?): String? {
        value?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toValue(str: String?): Attribute.Value? {
        str?.let {
            return gson.fromJson(it, Attribute.Value::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromSellerAddress(sellerAddress: SellerAddress?): String? {
        sellerAddress?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toSellerAddress(str: String?): SellerAddress? {
        str?.let {
            return gson.fromJson(it, SellerAddress::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromSeller(seller: Seller?): String? {
        seller?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toSeller(str: String?): Seller? {
        str?.let {
            return gson.fromJson(it, Seller::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromSellerReputation(sellerReputation: Seller.SellerReputation?): String? {
        sellerReputation?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toSellerReputation(str: String?): Seller.SellerReputation? {
        str?.let {
            return gson.fromJson(it, Seller.SellerReputation::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromTransactions(transactions: Seller.SellerReputation.Transactions?): String? {
        transactions?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toTransactions(str: String?): Seller.SellerReputation.Transactions? {
        str?.let {
            return gson.fromJson(it, Seller.SellerReputation.Transactions::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromRatings(ratings: Seller.SellerReputation.Transactions.Ratings?): String? {
        ratings?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toRatings(str: String?): Seller.SellerReputation.Transactions.Ratings? {
        str?.let {
            return gson.fromJson(it, Seller.SellerReputation.Transactions.Ratings::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromMetrics(metrics: Seller.SellerReputation.Metrics?): String? {
        metrics?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toMetrics(str: String?): Seller.SellerReputation.Metrics? {
        str?.let {
            return gson.fromJson(it, Seller.SellerReputation.Metrics::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromCancellations(cancellations: Seller.SellerReputation.Metrics.Cancellations?): String? {
        cancellations?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toCancellations(str: String?): Seller.SellerReputation.Metrics.Cancellations? {
        str?.let {
            return gson.fromJson(it, Seller.SellerReputation.Metrics.Cancellations::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromClaims(claims: Seller.SellerReputation.Metrics.Claims?): String? {
        claims?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toClaims(str: String?): Seller.SellerReputation.Metrics.Claims? {
        str?.let {
            return gson.fromJson(it, Seller.SellerReputation.Metrics.Claims::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromDelayedHandlingTime(delayed: Seller.SellerReputation.Metrics.DelayedHandlingTime?): String? {
        delayed?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toDelayedHandlingTime(str: String?): Seller.SellerReputation.Metrics.DelayedHandlingTime? {
        str?.let {
            return gson.fromJson(it, Seller.SellerReputation.Metrics.DelayedHandlingTime::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromSales(sales: Seller.SellerReputation.Metrics.Sales?): String? {
        sales?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toSales(str: String?): Seller.SellerReputation.Metrics.Sales? {
        str?.let {
            return gson.fromJson(it, Seller.SellerReputation.Metrics.Sales::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromEShopRubro(eshopRubro: Seller.Eshop.EShopRubro?): String? {
        eshopRubro?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toEShopRubro(str: String?): Seller.Eshop.EShopRubro? {
        str?.let {
            return gson.fromJson(it, Seller.Eshop.EShopRubro::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromEShop(eshop: Seller.Eshop?): String? {
        eshop?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toEShop(str: String?): Seller.Eshop? {
        str?.let {
            return gson.fromJson(it, Seller.Eshop::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromEShopList(list: List<Seller.Eshop.EshopLocation?>?): String? {
        var response = ""
        list?.let {
            for (i in list.indices) {
                response += if (i == 0) fromEShopLocation(list[i])
                else ";${fromEShopLocation(list[i])}"
            }
        } ?: return null
        return response
    }

    @TypeConverter
    fun toEShopList(concat: String?): List<Seller.Eshop.EshopLocation?>? {
        val newList = concat?.split(";")
        newList?.let {
            return it.map { str -> toEShopLocation(str) }
        } ?: return null
    }

    @TypeConverter
    fun fromEShopLocation(eshopLocation: Seller.Eshop.EshopLocation?): String? {
        eshopLocation?.let {
            return gson.toJson(it)
        } ?: return null
    }

    @TypeConverter
    fun toEShopLocation(str: String?): Seller.Eshop.EshopLocation? {
        str?.let {
            return gson.fromJson(it, Seller.Eshop.EshopLocation::class.java)
        } ?: return null
    }

    @TypeConverter
    fun fromNeighborhoodEshop(neighborhood: Seller.Eshop.EshopLocation.Neighborhood?): String? {
        return neighborhood?.id
    }

    @TypeConverter
    fun toNeighborhoodEshop(id: String?): Seller.Eshop.EshopLocation.Neighborhood? {
        return Seller.Eshop.EshopLocation.Neighborhood(id)
    }

    @TypeConverter
    fun fromCityEshop(city: Seller.Eshop.EshopLocation.City?): String? {
        return city?.id
    }

    @TypeConverter
    fun toCityEshop(id: String?): Seller.Eshop.EshopLocation.City? {
        return Seller.Eshop.EshopLocation.City(id)
    }

    @TypeConverter
    fun fromCountryEshop(country: Seller.Eshop.EshopLocation.Country?): String? {
        return country?.id
    }

    @TypeConverter
    fun toCountryEshop(id: String?): Seller.Eshop.EshopLocation.Country? {
        return Seller.Eshop.EshopLocation.Country(id)
    }

    @TypeConverter
    fun fromStateEshop(state: Seller.Eshop.EshopLocation.State?): String? {
        return state?.id
    }

    @TypeConverter
    fun toStateEshop(id: String?): Seller.Eshop.EshopLocation.State? {
        return Seller.Eshop.EshopLocation.State(id)
    }
}