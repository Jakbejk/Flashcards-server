package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.handler;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.AbstractException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@ControllerAdvice
public class ExceptionHandler extends ExceptionHandlerExceptionResolver {

    @org.springframework.web.bind.annotation.ExceptionHandler(AbstractException.class)
    public ResponseEntity<Object> handleControllerException(AbstractException ex, WebRequest req) {
        ErrorResponse response = new ErrorResponse(ex.getApplicationCode(), ex.getMessage());
        return new ResponseEntity<>(response, ex.getCode());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ErrorResponse {

        private int applicationCode;

        private String message;
    }
}
