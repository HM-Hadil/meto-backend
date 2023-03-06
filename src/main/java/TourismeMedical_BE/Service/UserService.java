package TourismeMedical_BE.Service;

import TourismeMedical_BE.Entity.Role;
import TourismeMedical_BE.Entity.Users;
import TourismeMedical_BE.dao.RoleDao;
import TourismeMedical_BE.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    public Users registerNewUser(Users users){
        return userDao.save(users);
    }

    public void initRolesAndUser(){
        //on a 3 role : admin , user , medecin

        Role adminRole = new Role();
        adminRole.setRoleName("admin");
        adminRole.setRole_description("admin role");
        roleDao.save(adminRole);

        Role medecinRole = new Role();
        medecinRole.setRoleName("medecin");
        medecinRole.setRole_description("medecin role");
        roleDao.save(medecinRole);

        Role userRole = new Role();
        userRole.setRoleName("user");
        userRole.setRole_description("user role");
        roleDao.save(userRole);



        //et on a 3 user  role , admin  , medecin user role

        Users medecinUser = new Users();
        medecinUser.setUserName("medecin");
        medecinUser.setUserFirstName("medecin first name");
        medecinUser.setUserLastName("medecin last name");
        medecinUser.setUserEmail("medecin@gmail.com");
        medecinUser.setUserPassword("medecin123");
        Set<Role> medecinRoles = new HashSet<>();
        medecinRoles.add(medecinRole);
        medecinUser.setRole(medecinRoles);
        userDao.save(medecinUser);

        Users users = new Users();
        users.setUserName("user");
        users.setUserFirstName("user first name");
        users.setUserLastName("user last name");
        users.setUserEmail("user@gmail.com");
        users.setUserPassword("user123");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        users.setRole(userRoles);
        userDao.save(users);


        Users admin = new Users();
        admin.setUserName("admin");
        admin.setUserFirstName("admin first name");
        admin.setUserLastName("admin last name");
        admin.setUserEmail("admin@gmail.com");
        admin.setUserPassword("admin123");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        admin.setRole(adminRoles);
        userDao.save(admin);

    }
}
