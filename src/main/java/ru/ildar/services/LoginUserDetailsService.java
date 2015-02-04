package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Person;
import ru.ildar.database.repositories.PersonDAO;

import java.util.Arrays;

/**
 * Service used by Spring Security to fetch the user by his username
 */
@Service
public class LoginUserDetailsService implements UserDetailsService
{
    @Autowired
    private PersonService personService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Person p = personService.getByUserName(username);
        return new User(username, p.getPassword(), true, true, true, true,
                Arrays.asList(new SimpleGrantedAuthority(p.getRoleName())));
    }
}
