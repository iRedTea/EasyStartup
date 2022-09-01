package site.easystartup.web.auth.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.easystartup.web.domain.model.User;
import site.easystartup.web.domain.request.LoginRequest;
import site.easystartup.web.domain.request.SignupRequest;
import site.easystartup.web.domain.response.MessageResponse;
import site.easystartup.web.domain.response.SuccessAuthenticationResponse;
import site.easystartup.web.domain.validation.ResponseErrorValidation;
import site.easystartup.web.project.dto.UserDto;
import site.easystartup.web.security.JWTUtil;
import site.easystartup.web.security.SecurityConstants;
import site.easystartup.web.service.AuthService;
import site.easystartup.web.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final ResponseErrorValidation responseErrorValidation;
    private final ModelMapper modelMapper;

    @PostMapping("/signup")
    public ResponseEntity<Object> registration(@Valid @RequestBody SignupRequest signupRequest,
                                                      BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        authService.createUser(signupRequest);

        return ResponseEntity.ok(new MessageResponse("Account registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authentication(@Valid @RequestBody LoginRequest loginRequest,
                                                   BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = SecurityConstants.TOKEN_PREFIX + jwtUtil.generateToken(loginRequest.getUsername());
        User user = userService.getUserByUsername(loginRequest.getUsername());
        SuccessAuthenticationResponse response = new SuccessAuthenticationResponse(jwt, modelMapper.map(user, UserDto.class));
        return ResponseEntity.ok().body(response);
    }


}
