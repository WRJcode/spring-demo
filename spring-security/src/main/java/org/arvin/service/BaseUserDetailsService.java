package org.arvin.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface BaseUserDetailsService extends UserDetailsService {

    UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException;
}
