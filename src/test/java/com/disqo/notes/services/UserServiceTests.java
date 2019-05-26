package com.disqo.notes.services;

import com.disqo.notes.asserts.UserAssert;
import com.disqo.notes.entities.NoteUser;
import com.disqo.notes.repositories.UserRepository;
import com.disqo.notes.requests.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    private UserService userService;


    @BeforeEach
    void initializeService() {
        userService = new UserService(userRepository);
    }

    @Test
    void savedUserHasCreatedOnDateSameLastEditedOnDateHasEmailAndPassword() {
        //given
        SignupRequest signupRequest = new SignupRequest("alex@iconos.mx","password");
        when(userRepository.save(any(NoteUser.class))).then(returnsFirstArg());
        //when
        NoteUser savedUser = userService.registerNewUser(signupRequest);
        //then
        UserAssert.assertThat(savedUser)
                .hasCreatedOnDate()
                .hasLastUpdatedOnDate()
                .createdOnDateEqualsLastUpdatedOnDate()
                .emailEquals(signupRequest.getEmail())
                .passwordEquals(signupRequest.getPassword());
    }

    @Test
    void whenSavingUserIfEnteredAlreadyRegisteredEmailThrowResponseStatusException() {
        //given
        NoteUser alreadySignedUser = new NoteUser("1","alex@iconos.mx","password",new Date(),new Date(),null);
        when(userRepository.findByEmail(alreadySignedUser.getEmail())).thenReturn(alreadySignedUser);
        SignupRequest badSignupRequest = new SignupRequest("alex@iconos.mx","password");
        //when then
        assertThrows(ResponseStatusException.class,() -> {
            NoteUser noteUser = userService.registerNewUser(badSignupRequest);
        },"A noteUser with this email already exists.");
    }
}
