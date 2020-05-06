package server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.data.DAOs.SecurityInfoDAO;
import server.data.DAOs.UserDAO;
import server.data.DTOs.LoginForm;
import server.data.DTOs.RegistrationForm;
import server.data.DTOs.UserTO;
import server.services.SecurityService;
import server.services.UserService;
import server.utility.Validator;
import server.utility.exceptions.WrongFormatException;

@RestController(value = "/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @PostMapping(value = "/renewToken")
    public ResponseEntity<?> renewToken(@RequestParam("userID") String userID,
                                        @RequestHeader("token") String token) {
        SecurityInfoDAO renewedToken = securityService.validateAndRenewToken(userID, token);
        return ResponseEntity.ok(renewedToken);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> tryToLogin(@RequestBody LoginForm loginForm) {
        if (Validator.isLoginFormValid(loginForm)) {
            UserDAO userDAO = userService.tryToLogIn(loginForm);
            SecurityInfoDAO newToken = securityService.getNewToken(userDAO.get_id().toString());
            return ResponseEntity.ok().header("token", newToken.getToken()).body(UserTO.map(userDAO));
        }
        throw new WrongFormatException("Invalid username and/or password!");
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> tryToRegister(@RequestBody RegistrationForm registrationForm) {
        if (Validator.isRegistrationFormValid(registrationForm)) {
            return ResponseEntity.ok(userService.tryToRegister(registrationForm));
        }
        throw new WrongFormatException("Invalid data format!");
    }
}
