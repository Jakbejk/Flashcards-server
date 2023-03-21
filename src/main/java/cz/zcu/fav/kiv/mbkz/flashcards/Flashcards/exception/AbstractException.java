package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractException extends RuntimeException {

    protected static final int APPLICATION_RESPONSE_CODE = 460;

    public HttpStatus getCode() {
        return HttpStatus.resolve(APPLICATION_RESPONSE_CODE);
    }

    public abstract int getApplicationCode();

}
