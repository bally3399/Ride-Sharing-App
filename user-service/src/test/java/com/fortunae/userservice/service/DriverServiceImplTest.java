package com.fortunae.userservice.service;

import com.fortunae.userservice.dtos.request.LoginRequest;
import com.fortunae.userservice.dtos.request.RegisterDriverRequest;
import com.fortunae.userservice.dtos.response.GetUserResponse;
import com.fortunae.userservice.dtos.response.LoginResponse;
import com.fortunae.userservice.dtos.response.RegisterDriverResponse;
import com.fortunae.userservice.exceptions.InvalidDetailsException;
import com.fortunae.userservice.exceptions.NotFoundException;
import com.fortunae.userservice.exceptions.UserExistsException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.fortunae.userservice.model.Role.DRIVER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class DriverServiceImplTest {

    @Autowired
    private DriverService driverService;

    private RegisterDriverResponse driverResponse;
    private RegisterDriverRequest driverRequest;
    private LoginResponse loginResponse;

    @BeforeEach
    public void setUp(){
        driverService.deleteAll();

        driverRequest = new RegisterDriverRequest();
        driverRequest.setEmail("rose@fortunae.com");
        driverRequest.setPassword("Password@1234");
        driverRequest.setFirstName("Rose");
        driverRequest.setPhoneNumber("09012345678");
        driverRequest.setUsername("Rossy");
        driverRequest.setLastName("Mary");
        driverRequest.setRole(DRIVER);
        driverRequest.setAvailable(true);
        driverRequest.setVehicleColor("white");
        driverRequest.setVehicleModel("2020");
        driverRequest.setLicensePlate("MNt112");
        driverResponse = driverService.registerDriver(driverRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("rose@fortunae.com");
        loginRequest.setPassword("Password@1234");
        loginResponse = driverService.login(loginRequest);

    }


    @Test
    public void registerDriverTest(){
        assertNotNull(driverResponse);
        assertThat(driverResponse.getMessage()).isEqualTo("Driver registered successfully");
    }

    @Test
    public void getDriver(){
        GetUserResponse user = driverService.getUser(driverResponse.getEmail());

        Assertions.assertThat(user.getEmail()).isEqualTo(driverResponse.getEmail());
        Assertions.assertThat(user.getFirstName()).isEqualTo(driverResponse.getFirstName());
        Assertions.assertThat(user.getLastName()).isEqualTo(driverResponse.getLastName());
        Assertions.assertThat(user.getPhoneNumber()).isEqualTo(driverResponse.getPhoneNumber());
        Assertions.assertThat(user.getId()).isNotNull();
    }

    @Test
    void testRegisterUserWithExistingEmail() {
        assertThrows(UserExistsException.class, () -> driverService.registerDriver(driverRequest));
    }

    @Test
    void testRegisterUserWithIncorrectEmail() {
        RegisterDriverRequest user = new RegisterDriverRequest();
        user.setEmail("badEmail.com");
        assertThrows(InvalidDetailsException.class, () -> driverService.registerDriver(user));
    }
    @Test
    void testRegisterUserWithBadPassword() {
        RegisterDriverRequest user = new RegisterDriverRequest();
        user.setPassword("badpassword");
        assertThrows(InvalidDetailsException.class, () -> driverService.registerDriver(user));
    }

    @Test
    public void testDeleteUser() {
        System.out.println(driverRequest);

        driverService.deleteUser(driverResponse.getEmail());
        assertThrows(NotFoundException.class, ()-> driverService.deleteUser(driverResponse.getEmail()));
    }

    @Test
    public void testThatCanLogin(){
        assertThat(loginResponse).isNotNull();
    }

}