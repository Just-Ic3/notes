package com.disqo.notes.sessions;

import com.disqo.notes.entities.NoteUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class NoteUserPrincipal implements UserDetails {

    private NoteUser noteUser;

    public NoteUserPrincipal(NoteUser noteUser) {
        this.noteUser = noteUser;
    }

    public String getId() {
        return noteUser.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return noteUser.getPassword();
    }

    @Override
    public String getUsername() {
        return noteUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
