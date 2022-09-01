package site.easystartup.web.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import site.easystartup.web.domain.request.UpdatePassportRequest;
import site.easystartup.web.domain.response.MessageResponse;
import site.easystartup.web.domain.response.SuccessAuthenticationResponse;
import site.easystartup.web.domain.validation.ResponseErrorValidation;
import site.easystartup.web.project.dto.UserDto;
import site.easystartup.web.security.JWTUtil;
import site.easystartup.web.security.SecurityConstants;
import site.easystartup.web.service.AuthService;
import site.easystartup.web.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Авторизация")
public class AuthController {
    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final ResponseErrorValidation responseErrorValidation;
    private final ModelMapper modelMapper;

    @PostMapping("/signup")
    @Operation(summary = "Создание нового аккаунта")
    public ResponseEntity<Object> registration(@Valid @RequestBody SignupRequest signupRequest,
                                                      BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        authService.createUser(signupRequest);

        return ResponseEntity.ok(new MessageResponse("Account registered successfully!"));
    }

    @PostMapping("/login")
    @Operation(summary = "Логин, получение JWT ключа и данных текущего пользователя")
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

    @PostMapping("/password")
    @Operation(summary = "обновление пароля и получение нового JWT")
    public ResponseEntity<Object> updatePassword(@Valid @RequestBody UpdatePassportRequest passportRequest,
                                                 BindingResult bindingResult,
                                                 Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        User user = authService.updatePassword(passportRequest, principal);


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = SecurityConstants.TOKEN_PREFIX + jwtUtil.generateToken(user.getUsername());
        SuccessAuthenticationResponse response = new SuccessAuthenticationResponse(jwt, modelMapper.map(user, UserDto.class));
        return ResponseEntity.ok().body(response);
    }


}
