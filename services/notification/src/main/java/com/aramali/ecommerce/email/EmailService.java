package com.aramali.ecommerce.email;

import com.aramali.ecommerce.kafka.order.Product;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {


    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(String email, String customerName, BigDecimal amount, String orderReference) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.setFrom("mouhammedaramali@gmail.com");
        final String templateName = EmailTemplates.PAYMENT_CONFIRMATION.getTemplate();

        Map<String,Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(variables);
        helper.setSubject(EmailTemplates.PAYMENT_CONFIRMATION.getSubject());
        try{
            String htmlTemplate = templateEngine.process(templateName, context);
            helper.setText(htmlTemplate, true);
            helper.setTo(email);
            javaMailSender.send(mimeMessage);
            log.info(String.format("INFO - Email sent to: %s", email));
        }catch (MessagingException e){
            log.warn(String.format("ERROR - Email not sent to: %s", email));
        }


        helper.setTo(email);

    }



    @Async
    public void sendOrderConfirmationEmail(String email, String customerName, BigDecimal amount, String orderReference, List<Product> productList) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.setFrom("mouhammedaramali@gmail.com");
        final String templateName = EmailTemplates.ORDER_CONFIRMATION.getTemplate();

        Map<String,Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("totalAmount", amount);
        variables.put("orderReference", orderReference);
        variables.put("products", productList);

        Context context = new Context();
        context.setVariables(variables);
        helper.setSubject(EmailTemplates.PAYMENT_CONFIRMATION.getSubject());
        try{
            String htmlTemplate = templateEngine.process(templateName, context);
            helper.setText(htmlTemplate, true);
            helper.setTo(email);
            javaMailSender.send(mimeMessage);
            log.info(String.format("INFO - Email sent to: %s", email));
        }catch (MessagingException e){
            log.warn(String.format("ERROR - Email not sent to: %s", email));
        }


        helper.setTo(email);

    }

}
