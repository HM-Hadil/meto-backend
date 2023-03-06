package TourismeMedical_BE.Controller;

import TourismeMedical_BE.Entity.Users;
import TourismeMedical_BE.Service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public String forAdmin(){
        return "this Url is only for admin";
    }

    @GetMapping({"forUser"})
    public String forUser(){
        return "this Url is only for users";
    }

    @GetMapping({"forMedecin"})
    public String forMedecin(){
        return "this Url is only for medecin";
    }
}
