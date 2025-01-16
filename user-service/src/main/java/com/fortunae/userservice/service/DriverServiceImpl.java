package com.fortunae.userservice.service;

import com.fortunae.userservice.dtos.request.LoginRequest;
import com.fortunae.userservice.dtos.request.RegisterDriverRequest;
import com.fortunae.userservice.dtos.response.GetUserResponse;
import com.fortunae.userservice.dtos.response.LoginResponse;
import com.fortunae.userservice.dtos.response.RegisterDriverResponse;
import com.fortunae.userservice.exceptions.InvalidDetailsException;
import com.fortunae.userservice.exceptions.NotFoundException;
import com.fortunae.userservice.exceptions.UserExistsException;
import com.fortunae.userservice.model.Driver;
import com.fortunae.userservice.repository.DriverRepository;
import com.fortunae.userservice.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.fortunae.userservice.utils.ValidationUtils.*;

@Service
public class DriverServiceImpl implements DriverService{

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public RegisterDriverResponse registerDriver(RegisterDriverRequest driverRequest) {
        validateFields(driverRequest.getPhoneNumber(), driverRequest.getEmail(), driverRequest.getPassword());
        doesUserExists(driverRequest.getEmail());
        Driver user = modelMapper.map(driverRequest, Driver.class);
        user = driverRepository.save(user);
        RegisterDriverResponse response = modelMapper.map(user, RegisterDriverResponse.class);
        response.setMessage("Driver registered successfully");
        return response;
    }
    private void validateFields(String phoneNumber, String email, String password) {
        if (!isValidPhoneNumber(phoneNumber))
            throw new InvalidDetailsException("The phone number you entered is not correct");
        if (!isValidEmail(email)) throw new InvalidDetailsException("The email you entered is not correct");
        if (!isValidPassword(password))
            throw new InvalidDetailsException("Password must be between 8 and 16 characters long, including at least one uppercase letter, one lowercase letter, one number, and one special character (e.g., @, #, $, %, ^).");
    }

    private void doesUserExists(String email){
        Driver user = driverRepository.findByEmail(email).orElse(null);
        if (user != null)throw new UserExistsException(String.format("User with email: %s already exits", email));
    }


    @Override
    public void deleteUser(String email) {
        Driver savedUser = driverRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(String.format("User with email: %s not found", email)));
        driverRepository.delete(savedUser);
    }

    @Override
    public GetUserResponse getUser(String email) {
        Driver user = driverRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(String.format("User with email: %s not found", email)));
        return modelMapper.map(user, GetUserResponse.class);
    }

    @Override
    public void deleteAll() {
        driverRepository.deleteAll();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        return checkLoginDetail(email, password);
    }

    private LoginResponse checkLoginDetail(String email, String password) {
        Optional<Driver> optionalUser = driverRepository.findByEmail(email);
        if (optionalUser.isPresent()){
            Driver user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                return loginResponseMapper(user);
            } else {
                throw new InvalidDetailsException("Invalid username or password");
            }
        } else {
            throw new InvalidDetailsException("Invalid username or password");
        }
    }

    private LoginResponse loginResponseMapper(Driver driver) {
        LoginResponse loginResponse = new LoginResponse();
        String accessToken = JwtUtils.generateAccessToken(driver.getId());
        BeanUtils.copyProperties(driver, loginResponse);
        loginResponse.setJwtToken(accessToken);
        loginResponse.setMessage("Login Successful");
        loginResponse.setRole(driver.getRole().toString());
        return loginResponse;
    }

    @Override
    public Driver getAvailableDriver(String id){
        Driver driver = driverRepository.findById(id).orElseThrow(()-> new NotFoundException("Driver not found"));
        driver.setAvailable(true);
        return driver;
    }
    
}
