package tech.skullprogrammer.core.model;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import tech.skullprogrammer.core.service.MailService;

@CheckedTemplate
public class Templates {

    public static native TemplateInstance welcome(String urlResetPassword);

    public static native TemplateInstance resetPassword(MailService.EmailModel model);


}
