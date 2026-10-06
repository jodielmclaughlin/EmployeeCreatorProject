package io.nology.project.auth;

import io.nology.project.auth.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserRepository repo;

    public AppUserDetailsService(AppUserRepository repo){
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = this.repo.findByEmail(email)
                .orElseThrow(() -> new UserNameNotFoundException("No account found for email: " + email));
        return new AppUserDetails(user);
    }
}
