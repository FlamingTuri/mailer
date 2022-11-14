package it.mailer.util

import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory

object Constants {

    const val APPLICATION_NAME = "Mailer service"

    val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()
}
