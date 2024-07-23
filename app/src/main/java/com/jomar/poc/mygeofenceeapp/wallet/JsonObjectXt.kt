package com.jomar.poc.mygeofenceeapp.wallet

import com.jomar.poc.mygeofenceeapp.AUD
import com.jomar.poc.mygeofenceeapp.IAT
import com.jomar.poc.mygeofenceeapp.ISSUER_ID
import com.jomar.poc.mygeofenceeapp.OWNER_EMAIL_ADDRESS
import com.jomar.poc.mygeofenceeapp.TYPE
import com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_class.FlightClasse
import com.jomar.poc.mygeofenceeapp.wallet.pass.newTemptative.w_obj.WalletObjRequest
import com.jomar.poc.mygeofenceeapp.wallet.pass.request.JwtParamsEnum
import com.jomar.poc.mygeofenceeapp.wallet.pass.request.ObjectParamsEnum
import com.jomar.poc.mygeofenceeapp.wallet.pass.request.WalletParamsEnum
import org.json.JSONArray
import org.json.JSONObject



fun createWalletClassJson(flightClasse: FlightClasse): JSONObject {
    val json = JSONObject()

    json.put(WalletParamsEnum.ID.param, "$ISSUER_ID.${flightClasse.id}")
    json.put(WalletParamsEnum.ISSUER_NAME.param, flightClasse.issuerName)

    val localizedIssuerName = JSONObject()
    val defaultValue = JSONObject()
    defaultValue.put(WalletParamsEnum.LANGUAGE.param, flightClasse.localizedIssuerName?.defaultValue?.language)
    defaultValue.put(WalletParamsEnum.VALUE.param, flightClasse.localizedIssuerName?.defaultValue?.value)
    localizedIssuerName.put(WalletParamsEnum.DEFAULT_VALUE.param, defaultValue)
    json.put(WalletParamsEnum.LOCALIZED_ISSUER_NAME.param, localizedIssuerName)

    val flightHeader = JSONObject()
    val carrier = JSONObject()
    carrier.put(WalletParamsEnum.CARRIER_IATA_CODE.param, flightClasse.flightHeader?.carrier?.carrierIataCode)

    val airlineLogo = JSONObject()
    val sourceUri = JSONObject()
    sourceUri.put(WalletParamsEnum.URI.param, flightClasse.flightHeader?.carrier?.airlineLogo?.sourceUri?.uri)
    airlineLogo.put(WalletParamsEnum.SOURCE_URI.param, sourceUri)

    val contentDescription = JSONObject()
    val defaultValueDescription = JSONObject()
    defaultValueDescription.put(WalletParamsEnum.LANGUAGE.param,flightClasse.flightHeader?.carrier?.airlineLogo?.contentDescription?.defaultValue?.language)
    defaultValueDescription.put(WalletParamsEnum.VALUE.param, flightClasse.flightHeader?.carrier?.airlineLogo?.contentDescription?.defaultValue?.value)
    contentDescription.put(WalletParamsEnum.DEFAULT_VALUE.param, defaultValueDescription)
    airlineLogo.put(WalletParamsEnum.CONTENT_DESCRIPTION.param, contentDescription)

    carrier.put(WalletParamsEnum.AIRLINE_LOGO.param, airlineLogo)
    flightHeader.put(WalletParamsEnum.CARRIER.param, carrier)
    flightHeader.put(WalletParamsEnum.FLIGHT_NUMBER.param, flightClasse.flightHeader?.flightNumber)
    json.put(WalletParamsEnum.FLIGHT_HEADER.param, flightHeader)

    val origin = JSONObject()
    origin.put(WalletParamsEnum.ORIGIN_AIRPORT_IATA_CODE.param, flightClasse.origin?.airportIataCode)
    origin.put(WalletParamsEnum.ORIGIN_TERMINAL.param, flightClasse.origin?.terminal)
    origin.put(WalletParamsEnum.ORIGIN_GATE.param, flightClasse.origin?.gate)
    json.put(WalletParamsEnum.ORIGIN.param, origin)

    val destination = JSONObject()
    destination.put(WalletParamsEnum.DESTINATION_AIRPORT_IATA_CODE.param, flightClasse.destination?.airportIataCode)
    destination.put(WalletParamsEnum.DESTINATION_TERMINAL.param, flightClasse.destination?.terminal)
    destination.put(WalletParamsEnum.DESTINATION_GATE.param, flightClasse.destination?.gate)
    json.put(WalletParamsEnum.DESTINATION.param, destination)

    json.put(WalletParamsEnum.LOCAL_SCHEDULED_DEPARTURE_DATE_TIME.param, flightClasse.localScheduledDepartureDateTime)
    json.put(WalletParamsEnum.REVIEW_STATUS.param, flightClasse.reviewStatus)
    json.put(WalletParamsEnum.HEX_BACKGROUND_COLOR.param, flightClasse.hexBackgroundColor)

    val heroImage = JSONObject()
    val heroImageSourceUri = JSONObject()
    heroImageSourceUri.put(WalletParamsEnum.URI.param, flightClasse.heroImage?.sourceUri?.uri)
    heroImage.put(WalletParamsEnum.SOURCE_URI.param, heroImageSourceUri)

    val heroImageContentDescription = JSONObject()
    val heroImageDefaultValue = JSONObject()
    heroImageDefaultValue.put(WalletParamsEnum.LANGUAGE.param, flightClasse.heroImage?.contentDescription?.defaultValue?.language)
    heroImageDefaultValue.put(WalletParamsEnum.VALUE.param, flightClasse.heroImage?.contentDescription?.defaultValue?.value)
    heroImageContentDescription.put(WalletParamsEnum.DEFAULT_VALUE.param, heroImageDefaultValue)
    heroImage.put(WalletParamsEnum.CONTENT_DESCRIPTION.param, heroImageContentDescription)

    json.put(WalletParamsEnum.HERO_IMAGE.param, heroImage)

    return json
}

fun getJwtObj(classRequest: JSONObject, objectRequest: JSONObject):JSONObject{
    val json = JSONObject()
    json.put(JwtParamsEnum.ISS.param, OWNER_EMAIL_ADDRESS)
    json.put(JwtParamsEnum.TYPE.param, TYPE)
    json.put(JwtParamsEnum.IAT.param, IAT)
    json.put(JwtParamsEnum.AUD.param, AUD)
    val origins = JSONArray()
    origins.put("www.voegol.com.br")
    json.put(JwtParamsEnum.ORIGIN.param, origins)
    val payload = JSONObject()
    val flightObjects = JSONArray()
    flightObjects.put(objectRequest)
    val flightClass = JSONArray()
    flightClass.put(classRequest)
    payload.put(JwtParamsEnum.FLIGHT_CLASSES.param, flightClass)
    payload.put(JwtParamsEnum.FLIGHT_OBJECTS.param, flightObjects)
    json.put(JwtParamsEnum.PAYLOAD.param, payload)
    return json
}

fun createWalletObjJson(objectParams: WalletObjRequest):JSONObject{
    val json = JSONObject()
    json.put(ObjectParamsEnum.ID.param, "$ISSUER_ID.${objectParams.id}")
    json.put(ObjectParamsEnum.CLASS_ID.param, "${objectParams.classId}")
    json.put(ObjectParamsEnum.STATE.param, objectParams.state)
    json.put(ObjectParamsEnum.PASSENGER_NAME.param, objectParams.passengerName)

    val reservationInfo = JSONObject()
    reservationInfo.put(ObjectParamsEnum.RESERVATION_CONFIRM.param, objectParams.reservationInfo?.confirmationCode)
    reservationInfo.put(ObjectParamsEnum.RESERVATION_ETICKET.param, objectParams.reservationInfo?.eticketNumber)
    json.put(ObjectParamsEnum.RESERVATION_INFO.param, reservationInfo)
    val boardingAndSeatingInfo = JSONObject()
    boardingAndSeatingInfo.put(ObjectParamsEnum.BOARDING_AND_SEATING_GROUP.param, objectParams.boardingAndSeatingInfo?.boardingGroup)
    boardingAndSeatingInfo.put(ObjectParamsEnum.BOARDING_AND_SEATING_SEAT_NIMBER.param, objectParams.boardingAndSeatingInfo?.seatNumber)
    boardingAndSeatingInfo.put(ObjectParamsEnum.BOARDING_AND_SEATING_SEAT_CLASS.param, objectParams.boardingAndSeatingInfo?.seatClass)
    json.put(ObjectParamsEnum.BOARDING_AND_SEATING_INFO.param, boardingAndSeatingInfo)
    val barcode = JSONObject()
    barcode.put(ObjectParamsEnum.BARCODE_TYPE.param, objectParams.barcode?.type)
    barcode.put(ObjectParamsEnum.BARCODE_VALUE.param, objectParams.barcode?.value)
    barcode.put(ObjectParamsEnum.BARCODE_TEXT.param, objectParams.barcode?.alternateText)
    json.put(ObjectParamsEnum.BARCODE.param, barcode)

    return json
}
