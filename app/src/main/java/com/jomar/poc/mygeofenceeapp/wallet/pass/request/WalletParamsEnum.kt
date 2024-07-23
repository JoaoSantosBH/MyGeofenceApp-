package com.jomar.poc.mygeofenceeapp.wallet.pass.request

enum class WalletParamsEnum(val param: String) {
    ID("id"),
    ISSUER_NAME("issuerName"),
    LOCALIZED_ISSUER_NAME("localizedIssuerName"),
    DEFAULT_VALUE("defaultValue"),
    LANGUAGE("language"),
    VALUE("value"),
    FLIGHT_HEADER("flightHeader"),
    CARRIER("carrier"),
    CARRIER_IATA_CODE("carrierIataCode"),
    AIRLINE_LOGO("airlineLogo"),
    SOURCE_URI("sourceUri"),
    URI("uri"),
    CONTENT_DESCRIPTION("contentDescription"),
    FLIGHT_NUMBER("flightNumber"),
    ORIGIN("origin"),
    ORIGIN_AIRPORT_IATA_CODE("airportIataCode"),
    ORIGIN_TERMINAL("terminal"),
    ORIGIN_GATE("gate"),
    DESTINATION("destination"),
    DESTINATION_AIRPORT_IATA_CODE("airportIataCode"),
    DESTINATION_TERMINAL("terminal"),
    DESTINATION_GATE("gate"),
    LOCAL_SCHEDULED_DEPARTURE_DATE_TIME("localScheduledDepartureDateTime"),
    REVIEW_STATUS("reviewStatus"),
    HEX_BACKGROUND_COLOR("hexBackgroundColor"),
    HERO_IMAGE("heroImage"),
}

enum class JwtParamsEnum(val param: String) {
    ISS("iss"),
    AUD("aud"),
    TYPE("typ"),
    IAT("iat"),
    ORIGIN("origins"),
    FLIGHT_OBJECTS("flightObjects"),
    FLIGHT_CLASSES("flightClasses"),
    PAYLOAD("payload");
}

enum class ObjectParamsEnum(val param: String) {
    ID("id"),
    CLASS_ID("classId"),
    STATE("state"),
    PASSENGER_NAME("passengerName"),
    RESERVATION_INFO("reservationInfo"),
    RESERVATION_CONFIRM("confirmationCode"),
    RESERVATION_ETICKET("eticketNumber"),
    BOARDING_AND_SEATING_INFO("boardingAndSeatingInfo"),
    BOARDING_AND_SEATING_GROUP("boardingGroup"),
    BOARDING_AND_SEATING_SEAT_NIMBER("seatNumber"),
    BOARDING_AND_SEATING_SEAT_CLASS("seatClass"),


    BARCODE("barcode"),
    BARCODE_TYPE("type"),
    BARCODE_VALUE("value"),
    BARCODE_TEXT("alternateText");
}

//{
//  "id": "ISSUER_ID.FLIGHT_CLASS_ID",
//  "issuerName": "Gol Linhas Aereas",
//  "localizedIssuerName": {
//    "defaultValue": {
//      "language": "en-US",
//      "value": "Gol Linhas Aereas"
//    }
//  },
//  "flightHeader": {
//    "carrier": {
//      "carrierIataCode": "GR",
//      "airlineLogo": {
//        "sourceUri": {
//          "uri": "https://images.unsplash.com/photo-1610642372651-fe6e7bc209ef?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=660&h=660"
//        },
//        "contentDescription": {
//          "defaultValue": {
//            "language": "en-US",
//            "value": "LOGO_IMAGE_DESCRIPTION"
//          }
//        }
//      }
//    },
//    "flightNumber": "1234"
//  },
//  "origin": {
//    "airportIataCode": "CNF",
//    "terminal": "4",
//    "gate": "15A"
//  },
//  "destination": {
//    "airportIataCode": "GRU",
//    "terminal": "DEST_TERMINAL",
//    "gate": "DEST_GATE"
//  },
//  "localScheduledDepartureDateTime": "2023-04-28T14:22",
//  "reviewStatus": "UNDER_REVIEW",
//  "hexBackgroundColor": "#ff7b00",
//  "heroImage": {
//    "sourceUri": {
//      "uri": "https://images.unsplash.com/photo-1581012771300-224937651c42?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1032&h=336"
//    },
//    "contentDescription": {
//      "defaultValue": {
//        "language": "en-US",
//        "value": "HERO_IMAGE_DESCRIPTION"
//      }
//    }
//  }
//}


