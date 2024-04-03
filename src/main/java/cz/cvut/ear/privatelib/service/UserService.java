package cz.cvut.ear.privatelib.service;

import cz.cvut.ear.privatelib.dao.UserDao;
import cz.cvut.ear.privatelib.dto.UserDto;
import cz.cvut.ear.privatelib.model.Cart;
import cz.cvut.ear.privatelib.model.Role;
import cz.cvut.ear.privatelib.model.Title;
import cz.cvut.ear.privatelib.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;




@Service(value = "userService")
public class UserService implements UserDetailsService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserDao userDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    public User findOne(String username) {
        return userDao.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public User getUserById(Integer id) {
        return userDao.find(id);
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    @Transactional
    public User save(UserDto user) {

        User nUser = user.getUserFromDto();
        nUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        if(nUser.getEmail().split("@")[1].equals("admin.edu")){
            role = roleService.findByName("ADMIN");
            roleSet.add(role);
        }

        nUser.setRoles(roleSet);
        return userDao.save(nUser);
    }

    @Transactional
    public User update(UserDto userDto) {
        User existingUser = userDao.findByUsername(userDto.getUsername());

        if (existingUser == null) {
            throw new RuntimeException("User not found with username: " + userDto.getUsername());
        }

        existingUser.setUsername(userDto.getUsername());
        existingUser.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        existingUser.setEmail(userDto.getEmail());

        return userDao.update(existingUser);
    }

    //ДО ОБНОВЛЕНИЯ:::::::::::::::::::::::::::: ВОЗМОЖНО УБРАТЬ ПРИДЕТСЯ
    /**
     * Save user in database
//     * @param user
     */

//    private void createCart(User user) {
//        final Cart cart = new Cart();
//        user.setCart(cart);
//        cart.setOwner(user);
//    }

    @Transactional(readOnly = true)
    public boolean exists(String username) {
        return userDao.findByUsername(username) != null;
    }





}
