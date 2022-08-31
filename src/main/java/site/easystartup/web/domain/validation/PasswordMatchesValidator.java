package site.easystartup.web.domain.validation;

import site.easystartup.web.domain.annotation.PasswordMatches;
import site.easystartup.web.domain.request.SignupRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        SignupRequest signupRequest = (SignupRequest) o;

        return signupRequest.getPassword().equals(signupRequest.getConfirmPassword());
    }
}