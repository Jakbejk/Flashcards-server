package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractException extends RuntimeException {

    public abstract HttpStatus getCode();

    public abstract int getApplicationCode();

}
