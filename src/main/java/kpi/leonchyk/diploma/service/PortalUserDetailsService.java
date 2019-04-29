package kpi.leonchyk.diploma.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kpi.leonchyk.diploma.domain.User;
import kpi.leonchyk.diploma.domain.Role;
import kpi.leonchyk.diploma.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PortalUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            final User user = userRepo.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("No user found with username: " + username);
            }

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    user.isActive(), true, true, true, getAuthorities(user.getRoles()));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        return getGrantedAuthorities(getRoles(roles));
    }

    private List<String> getRoles(final Collection<Role> roles) {
        final List<String> roleList = new ArrayList<String>();
        for (final Role role : roles) {
            roleList.add(role.getRole());
        }
        return roleList;
    }

    private List<GrantedAuthority> getGrantedAuthorities(final List<String> roles) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}