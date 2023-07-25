package com.anggar.miniproj.booksalong.security;

import com.anggar.miniproj.booksalong.data.entity.Role;
import com.anggar.miniproj.booksalong.data.entity.Scope;
import com.anggar.miniproj.booksalong.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> {
                    var authorities = getGrantedAuthorities(getScopes(user.getRoles()));

                    return new AuthUserDetails(user.getId(), user.getEmail(), authorities);
                })
                .orElse(null);
    }

    private List<String> getScopes(Collection<Role> roles) {
        List<String> scopes = new ArrayList<>();
        List<Scope> collection = new ArrayList<>();
        for (var role : roles) {
            scopes.add(role.getName());
            collection.addAll(role.getScopes());
        }
        for (var item : collection) {
            scopes.add(item.getName());
        }
        return scopes;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> scopes) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String scope : scopes) {
            authorities.add(new SimpleGrantedAuthority(scope));
        }
        return authorities;
    }
}
