package com.aramali.ecommerce.repository;

import com.aramali.ecommerce.kafka.payment.PaymentConfirmation;
import com.aramali.ecommerce.notification.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notification,String> {
}
