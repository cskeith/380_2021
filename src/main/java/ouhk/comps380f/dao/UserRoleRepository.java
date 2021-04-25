package ouhk.comps380f.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ouhk.comps380f.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}
