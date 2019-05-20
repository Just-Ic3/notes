package com.disqo.notes.services;

import com.disqo.notes.entities.NoteUser;
import com.disqo.notes.repositories.UserRepository;
import com.disqo.notes.sessions.NoteUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        NoteUser noteUser = userRepository.findByEmail(email);
        if(noteUser == null) {
            throw new UsernameNotFoundException(email);
        }
        return new NoteUserPrincipal(noteUser);
    }
}
