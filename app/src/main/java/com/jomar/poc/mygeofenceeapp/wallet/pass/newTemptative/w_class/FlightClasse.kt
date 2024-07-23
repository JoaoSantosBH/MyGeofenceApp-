package com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class


import com.google.gson.annotations.SerializedName
import com.jomar.poc.mygeofenceeapp.EMPTY_STRING
import com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class.Destination.Companion.FAKE_DESTINATION
import com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class.FlightHeader.Companion.FAKE_FLIGHT_HEADER
import com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class.HeroImage.Companion.FAKE_HERO_IMAGE
import com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class.LocalizedIssuerName.Companion.FAKE_LOCALIZED_ISSUER_NAME
import com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class.Origin.Companion.FAKE_ORIGIN

data class FlightClasse(
    @SerializedName("id") val id: String? = EMPTY_STRING,
    @SerializedName("issuerName") val issuerName: String? = EMPTY_STRING,
    @SerializedName("localizedIssuerName") val localizedIssuerName: LocalizedIssuerName? = LocalizedIssuerName(),
    @SerializedName("flightHeader") val flightHeader: FlightHeader? = FlightHeader(),
    @SerializedName("origin") val origin: Origin? = Origin(),
    @SerializedName("destination") val destination: Destination? = Destination(),
    @SerializedName("localScheduledDepartureDateTime") val localScheduledDepartureDateTime: String? = EMPTY_STRING,
    @SerializedName("reviewStatus") val reviewStatus: String? = EMPTY_STRING,
    @SerializedName("hexBackgroundColor") val hexBackgroundColor: String? = EMPTY_STRING,
    @SerializedName("heroImage") val heroImage: HeroImage? = HeroImage()
) {

    companion object {

        val FAKE_FLIGHT_CLASS = FlightClasse(
            id = "1234567890",
            issuerName = "Gol Linhas Aereas",
            localizedIssuerName = FAKE_LOCALIZED_ISSUER_NAME,
            flightHeader = FAKE_FLIGHT_HEADER,
            origin = FAKE_ORIGIN,
            destination = FAKE_DESTINATION,
            localScheduledDepartureDateTime = "2024-11-28T14:22",
            reviewStatus = "UNDER_REVIEW",
            hexBackgroundColor = "#ff7b00",
            heroImage = FAKE_HERO_IMAGE
        )

        fun getFakeFlightClasse() = FAKE_FLIGHT_CLASS

    }
}