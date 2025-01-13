package com.fortunae.userservice.service;

import com.fortunae.userservice.dtos.request.RegisterDriverRequest;
import com.fortunae.userservice.dtos.request.RegisterRiderRequest;
import com.fortunae.userservice.dtos.response.GetUserResponse;
import com.fortunae.userservice.dtos.response.RegisterDriverResponse;
import com.fortunae.userservice.dtos.response.RegisterUserResponse;
import com.fortunae.userservice.exceptions.InvalidDetailsException;
import com.fortunae.userservice.exceptions.NotFoundException;
import com.fortunae.userservice.exceptions.UserExistsException;
import com.fortunae.userservice.model.User;
import com.fortunae.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.fortunae.userservice.utils.ValidationUtils.*;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Override
    public RegisterUserResponse registerUser(RegisterRiderRequest request) {
        validateFields(request.getPhoneNumber(), request.getEmail(), request.getPassword());
        doesUserExists(request.getEmail());
        User user = modelMapper.map(request, User.class);
        user = userRepository.save(user);
        RegisterUserResponse response = modelMapper.map(user, RegisterUserResponse.class);
        response.setMessage("Rider registered successfully");
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
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null)throw new UserExistsException(String.format("User with email: %s already exits", email));
    }


    @Override
    public void deleteUser(String email) {
        User savedUser = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(String.format("User with email: %s not found", email)));
        userRepository.delete(savedUser);
    }

    @Override
    public GetUserResponse getUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(String.format("User with email: %s not found", email)));
        return modelMapper.map(user, GetUserResponse.class);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public RegisterDriverResponse registerDriver(RegisterDriverRequest driverRequest) {
        validateFields(driverRequest.getPhoneNumber(), driverRequest.getEmail(), driverRequest.getPassword());
        doesUserExists(driverRequest.getEmail());
        User user = modelMapper.map(driverRequest, User.class);
        user = userRepository.save(user);
        RegisterDriverResponse response = modelMapper.map(user, RegisterDriverResponse.class);
        response.setMessage("Driver registered successfully");
        return response;
    }

}
