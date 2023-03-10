package TourismeMedical_BE.dao;

import TourismeMedical_BE.Entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<Users, String> {
}
