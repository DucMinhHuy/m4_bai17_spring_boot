package huy.thuchanh.controller;

import huy.thuchanh.entities.User;
import huy.thuchanh.service.JwtService;
import huy.thuchanh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class UserRestController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @RequestMapping( value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUser(){
        return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
    }
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public ResponseEntity<Object> getUserById(@PathVariable int id){
        User user=userService.findById(id);
        if(user!=null){
            return new ResponseEntity<Object>(user,HttpStatus.OK);
        }
        return new ResponseEntity<Object>("not found user", HttpStatus.NO_CONTENT);
    }
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public ResponseEntity<String> createUser(@RequestBody User user){
        if(userService.add(user)){
            return new ResponseEntity<String>("create",HttpStatus.CREATED);
        }
        return new ResponseEntity<String>("user existed",HttpStatus.BAD_REQUEST);
    }
    @RequestMapping(value = "/user/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUserById(@PathVariable int id){
        userService.delete(id);
        return new ResponseEntity<String>("delete",HttpStatus.OK);
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String>login(HttpServletRequest request,@RequestBody User user){
        String result ="";
        HttpStatus httpStatus=null;
        try{
            if(userService.checkLogin(user)){
                result =jwtService.generateTokenLogin(user.getUsername());
                httpStatus=HttpStatus.OK;
            }
            result = "Wrong userId and password";
            httpStatus = HttpStatus.BAD_REQUEST;
        }catch (Exception e){
            result ="server Error";
            httpStatus =HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<String>(result,httpStatus);
    }

}
