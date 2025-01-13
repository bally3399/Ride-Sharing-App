package com.fortunae.userservice.service;

import com.fortunae.userservice.dtos.request.RegisterDriverRequest;
import com.fortunae.userservice.dtos.request.RegisterRiderRequest;
import com.fortunae.userservice.dtos.response.GetUserResponse;
import com.fortunae.userservice.dtos.response.RegisterDriverResponse;
import com.fortunae.userservice.dtos.response.RegisterUserResponse;
import com.fortunae.userservice.exceptions.InvalidDetailsException;
import com.fortunae.userservice.exceptions.UserExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.fortunae.userservice.model.Role.DRIVER;
import static com.fortunae.userservice.model.Role.RIDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;
    private RegisterUserResponse response;
    private RegisterDriverResponse driverResponse;
    private RegisterRiderRequest userRequest;
    private RegisterDriverRequest driverRequest;


    @BeforeEach
    public void setUp() {
        userService.deleteAll();

        userRequest = new RegisterRiderRequest();
        userRequest.setFirstName("Rider");
        userRequest.setLastName("Timi");
        userRequest.setEmail("timi@fortunae.com");
        userRequest.setPassword("Password@1234");
        userRequest.setPhoneNumber("08122233333");
        userRequest.setRole(RIDER);
        userRequest.setPreferredPaymentMethod("Bank transfer");
        response = userService.registerUser(userRequest);

        driverRequest = new RegisterDriverRequest();
        driverRequest.setEmail("temi@fortunae.com");
        driverRequest.setPassword("Password@1234");
        driverRequest.setFirstName("Temi");
        driverRequest.setPhoneNumber("09012345678");
        driverRequest.setLastName("Timi");
        driverRequest.setRole(DRIVER);
        driverRequest.setAvailable(true);
        driverRequest.setVehicleColor("white");
        driverRequest.setVehicleModel("2020");
        driverRequest.setLicensePlate("MNt112");
        driverResponse = userService.registerDriver(driverRequest);
    }
    @Test
    public void registerRiderTest(){
        assertNotNull(response);
        assertThat(response.getMessage()).isEqualTo("Rider registered successfully");
    }

    @Test
    public void registerDriverTest(){
        assertNotNull(driverResponse);
        assertThat(driverResponse.getMessage()).isEqualTo("Driver registered successfully");
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
    public void getDriver(){
        GetUserResponse user = userService.getUser(driverResponse.getEmail());

        assertThat(user.getEmail()).isEqualTo(driverResponse.getEmail());
        assertThat(user.getFirstName()).isEqualTo(driverResponse.getFirstName());
        assertThat(user.getLastName()).isEqualTo(driverResponse.getLastName());
        assertThat(user.getPhoneNumber()).isEqualTo(driverResponse.getPhoneNumber());
        assertThat(user.getId()).isNotNull();
    }

    @Test
    void testRegisterUserWithExistingEmail() {
        assertThrows(UserExistsException.class, () -> userService.registerUser(userRequest));
    }

    @Test
    void testRegisterUserWithIncorrectEmail() {
        RegisterRiderRequest user = new RegisterRiderRequest();
        user.setEmail("badEmail.com");
        assertThrows(InvalidDetailsException.class, () -> userService.registerUser(user));
    }
    @Test
    void testRegisterUserWithBadPassword() {
        RegisterRiderRequest user = new RegisterRiderRequest();
        user.setPassword("badpassword");
        assertThrows(InvalidDetailsException.class, () -> userService.registerUser(user));
    }

}