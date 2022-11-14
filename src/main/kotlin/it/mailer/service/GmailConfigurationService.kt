package it.mailer.service

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.GmailScopes
import io.quarkus.logging.Log
import it.mailer.util.Constants
import it.mailer.util.FileUtils
import java.io.FileInputStream
import java.io.InputStreamReader
import javax.inject.Singleton

@Singleton
class GmailConfigurationService {

    private val gmailService: Gmail

    init {
        val netHttpTransport = GoogleNetHttpTransport.newTrustedTransport()
        gmailService = Gmail.Builder(netHttpTransport, Constants.jsonFactory, getCredentials(netHttpTransport))
            .setApplicationName(Constants.APPLICATION_NAME)
            .build()
    }

    fun getGmailService(): Gmail {
        return gmailService
    }

    private fun getCredentials(netHttpTransport: NetHttpTransport): Credential? {
        FileUtils.getGmailConfigDirectory().toFile().mkdirs()

        val credentialsFile = FileUtils.getCredentialFile()
        if (!credentialsFile.toFile().exists()) {
            Log.error("Missing required credentialsFile in ${credentialsFile}.")
            Log.error("You can generate it from 'Credentials' section of google cloud console https://console.cloud.google.com/apis/credentials")
            Log.error("Select OAuth client ID specifying 'Desktop app' as the application type")
        }
        val inputStream = FileInputStream(credentialsFile.toString())

        val clientSecrets = GoogleClientSecrets.load(Constants.jsonFactory, InputStreamReader(inputStream))

        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder(
            netHttpTransport, Constants.jsonFactory, clientSecrets, getScopes()
        )
            .setDataStoreFactory(FileDataStoreFactory(FileUtils.getTokensDirectory().toFile()))
            .setAccessType("offline")
            .build()
        val receiver = LocalServerReceiver.Builder().setPort(8888).build()
        //returns an authorized Credential object.
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }

    private fun getScopes(): List<String> {
        return listOf(GmailScopes.GMAIL_SEND)
    }
}
