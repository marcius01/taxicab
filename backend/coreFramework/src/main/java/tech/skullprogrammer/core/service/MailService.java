package tech.skullprogrammer.core.service;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import tech.skullprogrammer.core.model.CustomConfig;
import tech.skullprogrammer.core.model.Templates;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

@ApplicationScoped
@Slf4j
public class MailService {

    @Inject
    CustomConfig apiConfig;
    @ConfigProperty(name = "quarkus.mailer.mock")
    Boolean mockMailer;
    @Inject
    ReactiveMailer mailer;

    public void sendResetPasswordMail(String userName, String lang, String otp, String email) {
        log.debug("Sending otp mail to {}", email);
        sendOTPMail(userName, lang, otp, email);
    }

    private String getResetUrl(String otp, String email) {
        var url = apiConfig.frontend().url() + apiConfig.frontend().resetPasswordRoute() + "?otp=" + otp;
        if (email != null) url += "&username=" + URLEncoder.encode(email, StandardCharsets.UTF_8);
        return url;
    }

    public void sendOTPMail(String userName, String lang, String otp, String email) {
        ResourceBundle bundle = ResourceBundle.getBundle("i18n/Bundle", Locale.forLanguageTag(lang));
        String subject = bundle.getString("email.reset.subject");

        TemplateInstance template = prepareResetPasswordEmail(bundle, lang, userName, otp);

        sendMail(
                Mail.withHtml(
                        email,
                        subject,
                        template.render()
                )
        );
    }

    private void sendMail(Mail mail) {
        if (mockMailer) {
            log.debug("Email sending disabled for email: {} - {}", mail.getSubject(), mail.getHtml());
        }
        Uni<Void> sending = mailer.send(mail);
        sending.subscribe().with(
                result -> log.info("Sent email to {} - Subject: {}", mail.getTo(), mail.getSubject()),
                failure -> log.error("Error sending email to {} - Subject: {} - {}", mail.getTo(), mail.getSubject(), failure.getLocalizedMessage())
        );
    }

    public TemplateInstance prepareResetPasswordEmail(ResourceBundle bundle, String lang, String name, String otp) {
        EmailModel model = new EmailModel();
        model.setTitle(bundle.getString("email.reset.title"));
        model.setOtp(otp);
//        model.setLogo(logo);
        model.setLanguage(lang);
        model.setHeader(MessageFormat.format(bundle.getString("email.reset.header"), name));
        model.setBody(bundle.getString("email.reset.body"));
        model.setFooter1(bundle.getString("email.reset.footer1"));
        model.setFooter2(bundle.getString("email.reset.footer2"));
        model.setFooter3(bundle.getString("email.reset.footer3"));
        model.setFooter4(bundle.getString("email.reset.footer4"));

        return Templates.resetPassword(model);
    }
    @Getter
    @Setter
    public class EmailModel {
        private String title;
        private String otp;
        private String logo;
        private String language;
        private String header;
        private String body;
        private String footer1;
        private String footer2;
        private String footer3;
        private String footer4;
    }

}
