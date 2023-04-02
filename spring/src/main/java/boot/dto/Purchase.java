package boot.dto;

import java.util.Set;

import boot.entity.Address;
import boot.entity.Customer;
import boot.entity.Order;
import boot.entity.OrderItem;
import lombok.Data;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
