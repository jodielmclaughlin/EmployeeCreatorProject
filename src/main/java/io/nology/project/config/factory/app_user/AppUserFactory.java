package io.nology.project.config.factory.app_user;

import com.github.javafaker.Faker;
import io.nology.project.auth.AppUserRepository;
import io.nology.project.auth.Role;
import io.nology.project.auth.entity.AppUser;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@Profile({"dev","test"})
public class AppUserFactory {
    private final Faker faker = new Faker();
    private final Set<String> usedEmails = new HashSet<>();
    private final AppUserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public AppUserFactory(AppUserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean hasUser(String email){
        Optional<AppUser> result = this.repo.findByEmail(email);
        return result.isPresent();
    }

    public AppUser create(AppUserFactoryOptions options){
        AppUser newUser = new AppUser();
        newUser.setEmail(generateUniqueEmail(options.email));
        newUser.setRole(options.role != null ? options.role : Role.EMPLOYEE);
        String rawPassword = options.password != null ? options.password : "password123";
        newUser.setPasswordHash(passwordEncoder.encode(rawPassword));
        return this.repo.saveAndFlush(newUser);
    }

    public AppUser create(){
        var opts = AppUserFactoryOptions.builder().build();
        return create(opts);
    }

    private String generateUniqueEmail(String email){
        if(email == null || email.isBlank()){
            email = faker.internet().emailAddress();
        }
        while (usedEmails.contains(email)){
            email = faker.internet().emailAddress();
        }
        usedEmails.add(email);
        return email;
    }
}
