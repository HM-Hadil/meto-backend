package TourismeMedical_BE.Controller;

import TourismeMedical_BE.Entity.Users;
import TourismeMedical_BE.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    //execute when we build the application
    @PostConstruct
     public void initRolesAndUsers(){
        userService.initRolesAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public Users registerNewUser(@RequestBody Users users){
        return userService.registerNewUser(users);

    }


    @GetMapping({"forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "this Url is only for admin";
    }

    @GetMapping({"forUser"})
    @PreAuthorize("hasRole('Users')")
    public String forUsers(){
        return "this Url is only for users";
    }

    @GetMapping({"forMedecin"})
    @PreAuthorize("hasRole('Medecin')")
    public String forMedecin(){
        return "this Url is only for medecin";
    }
}
