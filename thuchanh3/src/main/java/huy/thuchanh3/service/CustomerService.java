package huy.thuchanh3.service;

import huy.thuchanh3.model.Customers;
import huy.thuchanh3.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public Iterable<Customers> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customers> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public void save(Customers customers) {
        customerRepository.save(customers);
    }

    @Override
    public void remove(Long id) {
customerRepository.deleteById(id);
    }
}
