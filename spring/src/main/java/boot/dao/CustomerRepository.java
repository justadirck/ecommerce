package boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import boot.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByEmail(String theEmail);
    
}
