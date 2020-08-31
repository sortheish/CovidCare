package com.org.covidcare.utilities

/**
 * Created by ishwari s on 6/17/2020.
 */
const val PREFS_FILENAME = "prefs"
const val  COUNTRIES_URL = "https://disease.sh/v2/countries?yesterday=true&sort=cases&allowNull=false"
const val  STATE_URL = "https://api.rootnet.in/covid19-in/stats/history"
const val  DISTRICT_URL = "https://api.covid19india.org/state_district_wise.json"

const val FIREBASE_PHOTO_BASE_URL = "https://firebasestorage.googleapis.com/v0/b/covidcare-3474d.appspot.com/o/covidcare%2F"
const val FIREBASE_PHOTO_END_URL = "?alt=media"

const val INDIA = "India"
const val WORLD = "World"

const val COUNTRY_NAME = "country"
const val COUNTRY_CASES = "cases"
const val COUNTRY_RECOVERED = "recovered"
const val COUNTRY_DEATHS = "deaths"
const val COUNTRY_ACTIVE= "active"
const val COUNTRY_INFO = "countryInfo"
const val COUNTRY_flag = "flag"
const val COUNTRY_TODAY_CASES = "todayCases"
const val COUNTRY_TODAY_RECOVERED = "todayRecovered"
const val COUNTRY_TODAY_DEATHS= "todayDeaths"

const val STATE_NAME = "loc"
const val STATE_CASES = "totalConfirmed"
const val STATE_RECOVERED = "discharged"
const val STATE_DEATHS = "deaths"
const val STATE_DATA = "data"
const val STATE_DAY_DATA = "day"
const val STATE_REGIONAL_DATA = "regional"

const val DISTRICT_DATA = "districtData"
const val DISTRICT_CASES = "confirmed"
const val DISTRICT_RECOVERED = "recovered"
const val DISTRICT_DEATHS = "deceased"

const val REG_NUMBER = "^[7-9][0-9]{9}\$"
const val IS_LOGGED_IN = "isLoggedIn"
const val USER_NAME = "userName"
const val USER_MOBILE_NUMBER = "userMobileNumber"
const val UNIQUE_ID = "uniqueID"

const val FIREBASE_CONTENT = "content"
const val FIREBASE_DATE = "date"
const val FIREBASE_LINK = "link"
const val FIREBASE_PHOTO = "photo"
const val FIREBASE_SUBJECT = "subject"
const val FIREBASE_TITLE = "title"
const val FIREBASE_NEWSID = "newsId"
const val FIREBASE_IS_LOGGED_IN = "is_loggedin"
const val FIREBASE_FCM_TOKEN = "fcm_token"
const val DEVICE_ID = "device_id"
const val FIREBASE_DATABASE_COVID_CARE = "covid_care"
const val FIREBASE_DATABASE_REGI_USER = "registered_users"
const val FIREBASE_DATABASE_APP_USERS ="app_users"
