package site.easystartup.easystartupcore.project.domain.validation;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResponseErrorValidation {

    public Map<String, String> mapValidationService(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            if (!CollectionUtils.isEmpty(result.getAllErrors())) {
                for (ObjectError error : result.getAllErrors()) {
                    errorMap.put(error.getCode(), error.getDefaultMessage());
                }
            }

            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getCode(), error.getDefaultMessage());
            }

            return errorMap;
        }
        return null;
    }
}