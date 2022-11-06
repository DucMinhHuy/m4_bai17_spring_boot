package huy.thuchanh3.repository;

import huy.thuchanh3.model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends JpaRepository<Customers,Long> {
}
