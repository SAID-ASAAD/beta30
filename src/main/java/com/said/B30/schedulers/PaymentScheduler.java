package com.said.B30.schedulers;

import com.said.B30.businessrules.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentScheduler {

    private final OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(PaymentScheduler.class);

    @Scheduled(cron = "0 0 1 * * ?")
    public void updateOverduePayments() {
        logger.info("Running scheduled job to update overdue payment statuses...");
        orderService.updatePaymentStatusForOverdueOrders();
        logger.info("Scheduled job finished.");
    }
}
