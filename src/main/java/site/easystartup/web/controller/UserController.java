package site.easystartup.web.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.easystartup.web.domain.model.User;
import site.easystartup.web.domain.response.MessageResponse;
import site.easystartup.web.domain.validation.ResponseErrorValidation;
import site.easystartup.web.dto.UserDto;
import site.easystartup.web.exception.NoPermissionException;
import site.easystartup.web.forum.domain.model.Topic;
import site.easystartup.web.project.domain.model.Project;
import site.easystartup.web.project.dto.ProjectDto;
import site.easystartup.web.project.service.ProjectService;
import site.easystartup.web.request.UserRequest;
import site.easystartup.web.service.UserService;
import site.easystartup.web.util.FastChecksUtil;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController {
    private final site.easystartup.web.service.UserService userService;
    private final ModelMapper modelMapper;
    private final ResponseErrorValidation responseErrorValidation;
    private final FastChecksUtil checksUtil;

    //@
    @GetMapping("/user/current")
    public ResponseEntity<Object> getCurrentUser(Principal principal) {
        //if(principal == null)
        try {
            return ResponseEntity.ok().body(modelMapper.map(userService.getUserByPrincipal(principal), UserDto.class));
        } catch(Throwable e) {
            return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
        }

    }

    //@
    @GetMapping("/user/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable String username) {
        Long id = Long.parseLong(username);
        ResponseEntity<UserDto> result = checksUtil.nullIfValid(username);
        return result == null ? ResponseEntity.ok().body(modelMapper.map(userService.getUserByUsername(username), UserDto.class)) : result;
    }


    //@
    @PutMapping("/user/edit/{username}")
    public ResponseEntity<Object> userEdit(@PathVariable String username,@Valid @RequestBody UserRequest userRequest,
                                           BindingResult bindingResult, Principal principal) {
        if(!principal.getName().equals(username) && !userService.getUserByPrincipal(principal).isAdmin())
            throw new NoPermissionException(String.format("User %s can not edit profile of user %s", principal.getName(),
                    username));

        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        User editedUser = userService.editUser(username, userRequest);

        return ResponseEntity.ok().body(modelMapper.map(editedUser, UserDto.class));
    }
}
