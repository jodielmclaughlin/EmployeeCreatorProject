package io.nology.project.config.factory.app_user;


import io.nology.project.auth.Role;

public class AppUserFactoryOptions {
    String email;
    String password;
    Role role;

    private AppUserFactoryOptions(Builder builder){
        this.email = builder.email;
        this.password = builder.password;
        this.role = builder.role;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder{
        private String email;
        private String password;
        private Role role;

        public Builder email(String email){
            this.email = email;
            return this;
        }

        public Builder password(String password){
            this.password = password;
            return this;
        }

        public Builder role(Role role){
            this.role = role;
            return this;
        }

        public AppUserFactoryOptions build() {
            return new AppUserFactoryOptions(this);
        }
    }
}
