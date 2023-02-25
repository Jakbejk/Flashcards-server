package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception;

import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {
    private final String message;

    public <T> ValidationException(Set<ConstraintViolation<T>> violations) {
        if (violations == null) {
            this.message = "Constraint violation.";
        } else {
            StringBuilder messageBuilder = new StringBuilder("Constraint violation: \n");
            for (ConstraintViolation<T> violation : violations) {
                messageBuilder.append(violation.getMessage());
                messageBuilder.append("\n");
            }
            this.message = messageBuilder.toString();
        }
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
