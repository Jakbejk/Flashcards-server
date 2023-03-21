package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception;

import org.springframework.http.HttpStatus;

public final class SetErrorRegistry {


    public static final String SET_NOT_FOUND_MESSAGE = "Set with given id was not found.";
    public static final String ENOUGH_PERMISSIONS_FOR_SET_MESSAGE = "Set found, but user has not privileges.";
    public static final int SET_NOT_FOUND_CODE = 101;
    public static final int ENOUGH_PERMISSIONS_FOR_SET_CODE = 102;

    private SetErrorRegistry() {

    }

    public static class SetNotFoundException extends AbstractException {
        @Override
        public String getMessage() {
            return SET_NOT_FOUND_MESSAGE;
        }

        @Override
        public int getApplicationCode() {
            return SET_NOT_FOUND_CODE;
        }
    }

    public static class EnoughPermissionsForSet extends AbstractException {

        @Override
        public String getMessage() {
            return ENOUGH_PERMISSIONS_FOR_SET_MESSAGE;
        }

        @Override
        public int getApplicationCode() {
            return ENOUGH_PERMISSIONS_FOR_SET_CODE;
        }
    }

}
