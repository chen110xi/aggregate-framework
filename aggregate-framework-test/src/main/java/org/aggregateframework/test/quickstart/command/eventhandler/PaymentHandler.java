package org.aggregateframework.test.quickstart.command.eventhandler;

import org.aggregateframework.eventhandling.annotation.Backoff;
import org.aggregateframework.eventhandling.annotation.EventHandler;
import org.aggregateframework.eventhandling.annotation.Retryable;
import org.aggregateframework.test.hierarchicalmodel.command.domain.entity.DeliveryOrder;
import org.aggregateframework.test.hierarchicalmodel.command.domain.entity.DeliveryOrderInfo;
import org.aggregateframework.test.hierarchicalmodel.command.domain.repository.DeliveryOrderRepository;
import org.aggregateframework.test.quickstart.command.domain.event.PaymentConfirmedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by changming.xie on 4/7/16.
 */
@Service
public class PaymentHandler {

    @Autowired
    DeliveryOrderRepository deliveryOrderRepository;

    AtomicInteger counter = new AtomicInteger();

    @EventHandler(asynchronous = true, postAfterTransaction = true)
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 500, multiplier = 2))
    public void handlePaymentConfirmedEvent(PaymentConfirmedEvent event) {

        System.out.println("count:" + counter.incrementAndGet());
//        LockSupport.parkNanos(1000 * 1000 * 500);
    }

    public void recoverPaymentConfirmedEvent(PaymentConfirmedEvent event) {
        System.out.println("count:" + counter.incrementAndGet());
//        LockSupport.parkNanos(1000 * 1000 * 500);
    }

    private DeliveryOrder buildOrder() {
        DeliveryOrder deliveryOrder = new DeliveryOrder();

        deliveryOrder.setOrderInfo(new DeliveryOrderInfo());

        deliveryOrder.getOrderInfo().setDeliveryInfo("deliverinfo");

        return deliveryOrder;
    }
}
