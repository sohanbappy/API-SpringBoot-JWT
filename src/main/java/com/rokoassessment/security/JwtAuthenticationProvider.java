package com.rokoassessment.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.rokoassessment.models.JwtAuthenticationToken;
import com.rokoassessment.models.JwtUserDetails;
import com.rokoassessment.models.JwtUserLogin;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JwtValidator validator;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    
    protected JwtUserDetails retrieveUser(String email, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) usernamePasswordAuthenticationToken;
        String token = jwtAuthenticationToken.getToken();

        JwtUserDetails user = validator.validate(token);

        if (user == null) {
            throw new RuntimeException("JWT Token is incorrect");
        }

        return new JwtUserDetails(user.getFirst_name(),user.getEmail());
    }
    
    public JwtUserDetails retrieveUserNameAndEmail(JwtUserLogin loginUser,String givenToken) {
    	 String token = givenToken;
         JwtUserDetails user = validator.validate(token);

         if (user == null) {
             throw new RuntimeException("JWT Token is incorrect");
         }
//         return new JwtUserDetails(user.getFirst_name(),user.getEmail());
         if(user.getEmail().equals(loginUser.getEmail()) && user.getPassword().equals(loginUser.getPassword())) {
         return new JwtUserDetails(user.getFirst_name(),user.getEmail());
         }else {
        	 System.out.println("Email: "+user.getEmail());
        	 System.out.println("Pass: "+user.getPassword());
        	 throw new RuntimeException("JWT Token and Email-Password doesn't matched!!");
         }
    }
    
    @Override
    public boolean supports(Class<?> aClass) {
        return (JwtAuthenticationToken.class.isAssignableFrom(aClass));
    }
}
