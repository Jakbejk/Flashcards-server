package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.component.validator;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.ValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DtoInValidator {


    private final Validator validator;

    public <T> void validateDtoIn(T body) throws ValidationException {
        Set<ConstraintViolation<T>> violations = validator.validate(body);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }
    }
}
