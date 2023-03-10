package TourismeMedical_BE.Controller;

import TourismeMedical_BE.Entity.JwtRequest;
import TourismeMedical_BE.Entity.JwtResponse;
import TourismeMedical_BE.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtController {
    @Autowired
    private JwtService jwtService;


@PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody  JwtRequest jwtRequest) throws Exception{
    return jwtService.createJwtToken(jwtRequest);

    }

}
