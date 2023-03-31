package boot.service;

import boot.dto.Purchase;
import boot.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
