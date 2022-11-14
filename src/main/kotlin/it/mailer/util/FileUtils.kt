package it.mailer.util

import java.nio.file.Path
import java.nio.file.Paths

object FileUtils {

    fun getCredentialFile(): Path {
        return Paths.get(getGmailConfigDirectory().toString(), "credentials.json")
    }

    fun getTokensDirectory(): Path {
        return Paths.get(getGmailConfigDirectory().toString(), "tokens")
    }

    fun getGmailConfigDirectory(): Path {
        return Paths.get(getConfigDirectory().toString(), "gmail")
    }

    private fun getConfigDirectory(): Path {
        return Paths.get(System.getProperty("user.home"), ".mailer")
    }
}