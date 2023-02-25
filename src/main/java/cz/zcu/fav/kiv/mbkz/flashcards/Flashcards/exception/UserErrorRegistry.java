package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception;

import org.springframework.http.HttpStatus;

public final class UserErrorRegistry {

    public static final String USER_NOT_FOUND_MESSAGE = "User with given id was not found.";
    public static final int USER_NOT_FOUND_CODE = 201;

    private UserErrorRegistry() {

    }

    public static class UserNotFoundException extends AbstractException {

        @Override
        public String getMessage() {
            return USER_NOT_FOUND_MESSAGE;
        }

        @Override
        public HttpStatus getCode() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        @Override
        public int getApplicationCode() {
            return USER_NOT_FOUND_CODE;
        }
    }
}
