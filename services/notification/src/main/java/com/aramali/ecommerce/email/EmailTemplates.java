package com.aramali.ecommerce.email;

import lombok.Getter;

public enum EmailTemplates {

    PAYMENT_CONFIRMATION("payment-confirmation.html","Payment successfully processed"),
    ORDER_CONFIRMATION("order-confirmation.html","Order successfully processed"),
    ;
    @Getter
    private final String subject;
    @Getter
    private final String template;

    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}