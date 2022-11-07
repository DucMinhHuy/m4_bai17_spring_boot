package huy.thuchanh.rest;

import huy.thuchanh.entities.User;
import huy.thuchanh.service.JwtService;
import huy.thuchanh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
    private  final static String TOKEN_HEADER="authorization";
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServlet=(HttpServletRequest) request;
        String authToken = httpServlet.getHeader(TOKEN_HEADER);
        if(jwtService.validateTokenLogin(authToken)){
            String username= jwtService.getUsernameFromToken(authToken);
            User user= userService.loadUserByUsername(username);
            if(user!=null){
                boolean en= true;
                boolean ac=true;
                boolean cre =true;
                boolean acc=true;
                UserDetails userDetails=new org.springframework.security.core.userdetails.User(username,user.getPassword(),en,ac,cre,acc,user.getAuthorities());
                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServlet));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request,response);
    }
}
