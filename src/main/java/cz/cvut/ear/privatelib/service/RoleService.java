package cz.cvut.ear.privatelib.service;

import cz.cvut.ear.privatelib.dao.RoleDao;
import cz.cvut.ear.privatelib.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service(value = "roleService")
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role findByName(String name) {
        Role role = roleDao.findRoleByName(name);
        return role;
    }
}
