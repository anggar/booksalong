package com.anggar.miniproj.booksalong.config;

import com.anggar.miniproj.booksalong.data.entity.Role;
import com.anggar.miniproj.booksalong.data.entity.Scope;
import com.anggar.miniproj.booksalong.data.repository.RoleRepository;
import com.anggar.miniproj.booksalong.data.repository.ScopeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Component
public class SetupRoleData implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    ScopeRepository scopeRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;

        var readScope = createScopeIfNotFound("READ_SCOPE");
        var writeScope = createScopeIfNotFound("WRITE_SCOPE");

        createRoleIfNotFound("ROLE_ADMIN", Arrays.asList(readScope, writeScope));
        createRoleIfNotFound("ROLE_USER", Collections.singletonList(readScope));

        alreadySetup = true;
    }

    @Transactional
    private Scope createScopeIfNotFound(String name) {
        return scopeRepository.findByName(name)
                .orElseGet(() -> {
                    var scope = Scope.builder().name(name).build();
                    return scopeRepository.save(scope);
                });
    }

    @Transactional
    private void createRoleIfNotFound(String name, Collection<Scope> scopes) {
        roleRepository.findByName(name)
                .orElseGet(() -> {
                    var role = Role.builder().name(name).scopes(scopes).build();
                    return roleRepository.save(role);
                });
    }
}
