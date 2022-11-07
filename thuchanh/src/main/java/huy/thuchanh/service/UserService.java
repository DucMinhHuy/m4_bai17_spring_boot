package huy.thuchanh.service;

import huy.thuchanh.entities.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private User userT;
    public static List<User> listUser=new ArrayList<User>();
    static {
        User userKai=new User(1,"kai","123456");
        userKai.setRoles(new String[]{"ROLE_ADMIN"});
        User userSena=new User(2,"sena","123456");
        userSena.setRoles(new String[]{"ROLE_USER"});
        listUser.add(userKai);
        listUser.add(userSena);
    }
    public List<User> findAll(){
        return listUser;
    }
    public User findById(int id){
        for(User user : listUser){
            if(user.getId()==id){
                return user;
            }
        }
        return null;
    }
    public boolean add(User user){
        for(User userE : listUser){
            if(user.getId()==userE.getId() || StringUtils.equals(user.getUsername(),userE.getUsername())){
                return false;
            }
        }
        listUser.add(user);
        return true;
    }
    public void delete(int id){
        listUser.removeIf(user -> user.getId()==id);
    }
    public User loadUserByUsername(String userName) {
        for (User user : listUser) {
            if(user.getUsername().equals(userName)){
                return user;
            }
        }
        return null;
    }
    public boolean checkLogin(User user){
      for(User user1 : listUser){
          if(StringUtils.equals(user.getUsername(),user1.getUsername()) && StringUtils.equals(user.getPassword(),user1.getPassword())){
              return true;
          }
      }
      return false;
    }
}
