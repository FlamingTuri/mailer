package it.mailer

import io.quarkus.logging.Log
import io.quarkus.runtime.Quarkus
import io.quarkus.runtime.QuarkusApplication
import io.quarkus.runtime.annotations.QuarkusMain
import io.quarkus.runtime.configuration.ProfileManager

/**
 * Overriding default quarkus main allows to debug the application without having to install a quarkus plugin
 */
@QuarkusMain
object App {

    @JvmStatic
    fun main(args: Array<String>) {
        Quarkus.run(MyApp::class.java, *args)
    }

    class MyApp : QuarkusApplication {
        @Throws(Exception::class)
        override fun run(vararg args: String): Int {
            Log.info("App successfully initialized, active profile ${ProfileManager.getActiveProfile()}")
            // https://developers.google.com/gmail/api/quickstart/java
            Log.info("You can manage the required credentials from google cloud console https://console.cloud.google.com/welcome")
            Quarkus.waitForExit()
            return 0
        }
    }
}
