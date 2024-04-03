package cz.cvut.ear.privatelib.dao;


import cz.cvut.ear.privatelib.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends CrudRepository<Role, Long>{

    Role findRoleByName(String name);
}
