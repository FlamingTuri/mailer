package it.mailer.service

import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.services.gmail.model.Message
import io.quarkus.arc.profile.UnlessBuildProfile
import io.quarkus.logging.Log
import io.quarkus.runtime.Startup
import it.mailer.model.EmailDto
import it.mailer.util.Constants
import it.mailer.util.MimeMessageUtils
import it.mailer.util.Profiles
import javax.enterprise.context.ApplicationScoped

@Startup
@ApplicationScoped
@UnlessBuildProfile(Profiles.test)
class EmailService(private val gmailConfigurationService: GmailConfigurationService) {

    fun sendEmail(emailDto: EmailDto): Message {
        try {
            val mimeMessage = MimeMessageUtils.create(emailDto)
            val message = MimeMessageUtils.toMessage(mimeMessage)
            val service = gmailConfigurationService.getGmailService()
            val result = service.users().messages().send("me", message).execute()
            Log.info(result.toPrettyString())
            return result
        } catch (e: GoogleJsonResponseException) {
            if (e.details.code == 403) {
                Log.error("Unable to send message: " + e.details)
            }
            throw e
        }
    }
}
