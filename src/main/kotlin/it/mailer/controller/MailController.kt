package it.mailer.controller

import io.quarkus.arc.profile.UnlessBuildProfile
import io.quarkus.runtime.Startup
import it.mailer.model.EmailDto
import it.mailer.service.EmailService
import it.mailer.util.Profiles
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@UnlessBuildProfile(Profiles.test)
@Startup
@Path("/mailer")
class MailController(private val emailService: EmailService) {

    @GET
    @Path("/status")
    fun getStatus(): Response {
        return Response.ok("ready").build()
    }

    @POST
    @Path("/send")
    fun sendEmail(emailDto: EmailDto): Response {
        emailService.sendEmail(emailDto)
        return Response.ok().build()
    }
}
