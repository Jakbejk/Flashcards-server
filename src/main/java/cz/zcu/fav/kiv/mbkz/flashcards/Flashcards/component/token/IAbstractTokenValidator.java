package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.component.token;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.shadow.UserShort;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.AuthenticationException;

public interface IAbstractTokenValidator {

    /**
     * Validate token and check for permissions into database/LDAP.
     *
     * @param accessToken Token to be validated.
     * @return UserShort entity.
     * @throws AuthenticationException If token is invalid or user is not found.
     */
    UserShort validateToken(String accessToken) throws AuthenticationException;

    /**
     * Validate token and form response locally. Method should not return null, otherwise it should throw AuthenticationException.
     *
     * @param accessToken Token to be validated.
     * @return UserShort entity.
     * @throws AuthenticationException If token is invalid.
     */
    UserShort validateTokenLocal(String accessToken) throws AuthenticationException;

}
