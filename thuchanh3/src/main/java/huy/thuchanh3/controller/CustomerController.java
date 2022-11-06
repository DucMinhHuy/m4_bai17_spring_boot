package huy.thuchanh3.controller;

import huy.thuchanh3.model.Customers;
import huy.thuchanh3.service.CustomerService;
import huy.thuchanh3.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @GetMapping("/create")
   public ModelAndView showCreateForm(){
        ModelAndView modelAndView=new ModelAndView("/create");
        modelAndView.addObject("customer",new Customers());
        return modelAndView;
    }
    @PostMapping("/create")
    public ModelAndView saveCustomerForm(@ModelAttribute("customer") Customers customers){
        customerService.save(customers);
        ModelAndView modelAndView=new ModelAndView("/create");
        modelAndView.addObject("customer",new Customers());
        modelAndView.addObject("message","lol");
        return modelAndView;
    }
}
