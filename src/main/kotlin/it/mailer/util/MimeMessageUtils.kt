package it.mailer.util

import com.google.api.services.gmail.model.Message
import it.mailer.model.EmailDto
import org.apache.commons.codec.binary.Base64
import java.io.ByteArrayOutputStream
import java.util.Properties
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object MimeMessageUtils {

    fun create(emailDto: EmailDto): MimeMessage {
        val props = Properties()
        val session = Session.getDefaultInstance(props, null)
        val email = MimeMessage(session)
        email.setFrom(InternetAddress(emailDto.from))
        email.addRecipient(
            javax.mail.Message.RecipientType.TO,
            InternetAddress(emailDto.to)
        )
        email.subject = emailDto.subject
        email.setText(emailDto.body)
        return email
    }

    fun toMessage(emailContent: MimeMessage): Message {
        val buffer = ByteArrayOutputStream()
        emailContent.writeTo(buffer)

        val bytes = buffer.toByteArray()
        val encodedEmail = Base64.encodeBase64URLSafeString(bytes)

        return Message().apply {
            raw = encodedEmail
        }
    }
}