package com.bankingsystemapplication.service;

import com.bankingsystemapplication.form.LogoinRequest;
import com.bankingsystemapplication.model.Category;
import com.bankingsystemapplication.model.Customer;
import com.bankingsystemapplication.model.Roles;
import com.bankingsystemapplication.model.Users;
import com.bankingsystemapplication.repository.RolesRepository;
import com.bankingsystemapplication.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsersService  {

    private final RolesRepository rolesRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(RolesRepository rolesRepository, UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.rolesRepository = rolesRepository;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users saveAdmin(Users user){
        Roles role=rolesRepository.findRoleByName("ROLE_ADMIN");
        List<Roles> roles=new ArrayList<>();
        roles.add(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        return usersRepository.save(user);
    }

    public Roles saveRole(Roles role) {
        return rolesRepository.save(role);
    }
    public List<Users> findAllUsers() {
        return usersRepository.findAll();
    }
    public List<Roles> findAllRoles() {
        return rolesRepository.findAll();
    }

    public Users findUserById(Long id){

        return usersRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Sorry user not found"));
    }

    public Roles findRolesById(Long id){

        return rolesRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Sorry Role not found"));
    }
    public Roles  updateRoles(Roles roles){
        Roles roles1=rolesRepository.findById(roles.getId()).get();
        roles1.setName(roles.getName());
        return rolesRepository.save(roles1);
    }
    public Users updateUsers(Users users){
        Users usersPersisted=findUserById(users.getId());
        usersPersisted.setActivated(false);
        usersPersisted.setFirstname(users.getFirstname());
        usersPersisted.setLastname(users.getLastname());
        usersPersisted.setUsername(users.getUsername());
        Collection<Roles> roles=new ArrayList<>();
        usersPersisted.setRoles(roles);
        return usersRepository.save(usersPersisted);
    }
    public List<Users> findUserByEmail(String email){
        return usersRepository.findUsersByUsername(email).stream().distinct().collect(Collectors.toList());
    }

    public  boolean isRolesPresent(String name){
        Roles roles=rolesRepository.findRoleByName(name);
        if (roles!=null)
            return true;
        return false;
    }

    public  boolean isUsersPresent(String name){
      Users users=usersRepository.findByUsername(name);
        if (users!=null)
            return true;
        return false;
    }
    public Users loginUser(LogoinRequest logoinRequest){
         Users users=usersRepository.findByUsername(logoinRequest.getEmail());
        if(!passwordEncoder.matches(logoinRequest.getPassword(),users.getPassword())){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid credentatial");
        }
        return null;
    }


}
