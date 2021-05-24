package com.canlioya.technicaltest.data.network

import com.squareup.moshi.Json

/**
 * User data-transfer-object
 * used for mapping backend JSON schema
 *
 * @property userId
 * @property fullName
 * @property userName
 * @property email
 * @property address
 * @property phone
 * @property website
 * @property company
 * @constructor Create empty User d t o
 */
data class UserDTO(
    @Json(name = "id") val userId : Int,
    @Json(name = "name") val fullName : String,
    @Json(name = "username") val userName : String,
    val email : String?,
    val address : AddressDTO?,
    val phone : String?,
    val website : String?,
    val company: CompanyDTO?
)

/**
 * Address data-transfer-object
 * used for mapping backend JSON schema
 *
 * @property street
 * @property suite
 * @property city
 * @property zipcode
 * @property geo
 * @constructor Create empty Address
 */
data class AddressDTO(
    val street: String?,
    val suite: String?,
    val city: String?,
    val zipcode: String?,
    val geo: GeoDTO?
)

/**
 * Geo data-transfer-object
 * used for mapping backend JSON schema
 *
 * @property lat
 * @property lng
 * @constructor Create empty Geo
 */
data class GeoDTO(
    var lat: String?,
    var lng: String?
)

/**
 * Company data-transfer-object
 * used for mapping backend JSON schema
 *
 * @property name
 * @property catchPhrase
 * @property bs
 * @constructor Create empty Company
 */
data class CompanyDTO(
    var name: String?,
    var catchPhrase: String?,
    var bs: String?
)