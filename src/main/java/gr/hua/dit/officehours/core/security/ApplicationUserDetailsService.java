package gr.hua.dit.officehours.core.security;

import gr.hua.dit.officehours.core.model.Person;
import gr.hua.dit.officehours.core.repository.PersonRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of Spring's {@code UserDetailsService} for providing application users.
 */
@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    public ApplicationUserDetailsService(final PersonRepository personRepository) {
        if (personRepository == null) throw new NullPointerException();
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        // TODO EXERCISE:
        // 1. Find the Person via findByEmailAddressIgnoreCase(username.strip()).
        // 2. If absent, throw UsernameNotFoundException.
        // 3. Return ApplicationUserDetails(id, email, passwordHash, type).
        // Until implemented, the application starts, but login always fails.
        throw new UsernameNotFoundException("Exercise 1: load users from PersonRepository");
    }
}
