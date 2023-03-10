package TourismeMedical_BE.Service;

import TourismeMedical_BE.Entity.JwtRequest;
import TourismeMedical_BE.Entity.JwtResponse;
import TourismeMedical_BE.Entity.Users;
import TourismeMedical_BE.Util.JwtUtil;
import TourismeMedical_BE.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
   private AuthenticationManager authenticationManager;



    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String userName = jwtRequest.getUsername();
        String userPassword = jwtRequest.getPassword();
        authenticate(userName,userPassword);
        final   UserDetails userDetails = loadUserByUsername(userName);
       String newGenerateToken=  jwtUtil.generateToken(userDetails);
        Users users=  userDao.findById(userName).get();
        return  new JwtResponse(users,newGenerateToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Users users=  userDao.findById(username).get();
    if (users != null){
     return  new User(
                users.getUserName(),
                users.getUserPassword(),
                getAuthotorities(users)
        );
    }
    else {
           throw new UsernameNotFoundException("username is not valid !");
    }

    }

     public Set getAuthotorities(Users users){
        Set authorities = new HashSet();

        users.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        });
        return authorities;
     }


    private void authenticate(String userName, String userPassword) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,userPassword));

        }
        catch (DisabledException e){
            throw new Exception("user is disabled !");
        }
        catch (BadCredentialsException e){
            throw new Exception("bad Credential from user");
        }

    }


}
