package com.example.authentication_with_jwt.controller;

import com.example.authentication_with_jwt.custom_exception.ApiResponse;
import com.example.authentication_with_jwt.custom_exception.ApiResponsePayload;
import com.example.authentication_with_jwt.custom_exception.UserIdNotFound;
import com.example.authentication_with_jwt.entities.Address;
import com.example.authentication_with_jwt.entities.MyUser;
import com.example.authentication_with_jwt.models.*;
import com.example.authentication_with_jwt.services.AddressService;
import com.example.authentication_with_jwt.services.CustomUserDetails;
import com.example.authentication_with_jwt.services.JwtTokenProvider;
import com.example.authentication_with_jwt.services.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.example.authentication_with_jwt.custom_exception.UserAlreadyException;
import utils.CustomError;

@RestController
@RequestMapping(value = "/users/")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailService userDetailService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/hello")
    public String hello() {
        return "Correct username password. Welcome";
    }

    @RequestMapping(value = "/goodbye", method = RequestMethod.GET)
    public String goodbye() {
        return "Welcome from hello1";
    }

    @ExceptionHandler(UserAlreadyException.class)
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signupRequest) {
        try {
            Address address = addressService.getAddress(signupRequest.getWard(), signupRequest.getDistrict(), signupRequest.getCity());
            CustomUserDetails userDetails = userDetailService.signUp(signupRequest.getUsername(), signupRequest.getPassword(), address);
            final String jwt = jwtTokenProvider.generateToken(userDetails);
            SignupResponse signupResponse = new SignupResponse(true);
            return ResponseEntity.status(200).body(signupResponse);
        } catch (UserAlreadyException e) {
            return new ApiResponse<>().createResponse(CustomError.USER_NOT_FOUND, new ApiResponsePayload(CustomError.USER_ALREADY_EXISTS.getDescription()));
        }
    }

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    @RequestMapping(value = "/authentication", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            final CustomUserDetails userDetails = userDetailService.checkUsernamePassword(loginRequest.getUsername(), loginRequest.getPassword());
            final String jwt = jwtTokenProvider.generateToken(userDetails);

            LoginResponse loginResponse = new LoginResponse(true, new LoginResponsePayload(jwt));
            return ResponseEntity.status(200).body(loginResponse);
        } catch (UsernameNotFoundException error) {
            return new ApiResponse<>().createResponse(CustomError.USER_NOT_FOUND, new ApiResponsePayload(CustomError.USER_NOT_FOUND.getDescription()));
        } catch (BadCredentialsException error) {
            return new ApiResponse<>().createResponse(CustomError.PASSWORD_INCORRECT, new ApiResponsePayload(CustomError.PASSWORD_INCORRECT.getDescription()));
        }
    }

    @ExceptionHandler({UserIdNotFound.class})
    @RequestMapping(value = "/information", method = RequestMethod.GET)
    public ResponseEntity<?> getUserInfor(@RequestParam Integer userId) {
        try {
            UserDetails user = this.userDetailService.getUserById(userId);
            return ResponseEntity.status(200).body(user);
        } catch (UserIdNotFound err) {
            return ResponseEntity.status(500).body(err.getMessage());
        }
    }
}
