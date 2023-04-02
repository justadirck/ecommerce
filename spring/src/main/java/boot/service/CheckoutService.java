package boot.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import boot.dto.PaymentInfo;
import boot.dto.Purchase;
import boot.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;
    
}
