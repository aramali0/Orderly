package com.aramali.ecommerce.service;

import com.aramali.ecommerce.dto.PaymentRequest;
import com.aramali.ecommerce.mapper.PaymentMapper;
import com.aramali.ecommerce.notification.NotificationProducer;
import com.aramali.ecommerce.notification.PaymentNotificationRequest;
import com.aramali.ecommerce.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.RequestContextFilter;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(PaymentRequest request) {

        var payement = repository.save(mapper.toPayment(request));

        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethode(),
                        request.customer().firstName(),
                        request.customer().lastName(),
                        request.customer().email()
                )
        );

        return payement.getId();
    }
}
