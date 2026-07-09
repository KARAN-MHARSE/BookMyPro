package com.bookmypro.identity_service.feature.auth.login;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bookmypro.identity_service.common.enums.CredentialStatus;
import com.bookmypro.identity_service.model.Credential;

import lombok.Getter;

@Getter
public class CustomUserDetails implements UserDetails {


    private final Credential credential;


    public CustomUserDetails(Credential credential) {
        this.credential = credential;
    }


    @Override
    public String getUsername() {
        return credential.getEmail();
    }


    @Override
    public String getPassword() {
        return credential.getPassword();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return java.util.List.of();
    }


    @Override
    public boolean isAccountNonLocked() {

        return credential.getAccountLockedUntil() == null;
    }


    @Override
    public boolean isEnabled() {

        return credential.getStatus()
                == CredentialStatus.ACTIVE;
    }


    public UUID getCredentialId(){

        return credential.getCredentialId();
    }
}