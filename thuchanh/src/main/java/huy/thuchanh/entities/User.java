package huy.thuchanh.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = {"roles","authorities"})
@Data
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private String[] roles;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public List<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
        for(String role : roles){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
