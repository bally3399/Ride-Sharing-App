package com.fortunae.userservice.service;

import com.fortunae.userservice.dtos.request.LoginRequest;
import com.fortunae.userservice.dtos.request.RegisterRiderRequest;
import com.fortunae.userservice.dtos.response.GetUserResponse;
import com.fortunae.userservice.dtos.response.LoginResponse;
import com.fortunae.userservice.dtos.response.RegisterUserResponse;
import com.fortunae.userservice.exceptions.InvalidDetailsException;
import com.fortunae.userservice.exceptions.NotFoundException;
import com.fortunae.userservice.exceptions.UserExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.fortunae.userservice.model.Role.RIDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RiderServiceImplTest {
    @Autowired
    private RiderService userService;
    private RegisterUserResponse response;

    private RegisterRiderRequest userRequest;
    private LoginResponse loginResponse;


    @BeforeEach
    public void setUp() {
        userService.deleteAll();

        userRequest = new RegisterRiderRequest();
        userRequest.setFirstName("Rider");
        userRequest.setLastName("John");
        userRequest.setEmail("john@fortunae.com");
        userRequest.setPassword("Password@1234");
        userRequest.setPhoneNumber("08122233333");
        userRequest.setUsername("JohnDoe");
        userRequest.setRole(RIDER);
        userRequest.setPreferredPaymentMethod("Bank transfer");
        response = userService.registerUser(userRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("john@fortunae.com");
        loginRequest.setPassword("Password@1234");
        loginResponse = userService.login(loginRequest);


    }
    @Test
    public void registerRiderTest(){
        assertNotNull(response);
        assertThat(response.getMessage()).isEqualTo("Rider registered successfully");
        System.out.println(response);
    }


    @Test
    public void findRider(){
        GetUserResponse user = userService.getUser(response.getEmail());

        assertThat(user.getEmail()).isEqualTo(response.getEmail());
        assertThat(user.getFirstName()).isEqualTo(response.getFirstName());
        assertThat(user.getLastName()).isEqualTo(response.getLastName());
        assertThat(user.getPhoneNumber()).isEqualTo(response.getPhoneNumber());
        assertThat(user.getId()).isNotNull();
    }




    @Test
    public void testRegisterUserWithExistingEmail() {
        assertThrows(UserExistsException.class, () -> userService.registerUser(userRequest));
    }

    @Test
    public void testRegisterUserWithIncorrectEmail() {
        RegisterRiderRequest user = new RegisterRiderRequest();
        user.setEmail("badEmail.com");
        assertThrows(InvalidDetailsException.class, () -> userService.registerUser(user));
    }
    @Test
    public void testRegisterUserWithBadPassword() {
        RegisterRiderRequest user = new RegisterRiderRequest();
        user.setPassword("badpassword");
        assertThrows(InvalidDetailsException.class, () -> userService.registerUser(user));
    }

    @Test
    public void testDeleteUser() {
        System.out.println(userRequest);

        userService.deleteUser(response.getEmail());
        assertThrows(NotFoundException.class, ()-> userService.deleteUser(response.getEmail()));
    }

    @Test
    public void testThatCanLogin(){
        assertThat(loginResponse).isNotNull();
    }


}