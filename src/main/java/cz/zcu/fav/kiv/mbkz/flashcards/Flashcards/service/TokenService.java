package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.service;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dao.UserDao;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.User;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.shadow.UserShort;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.AuthenticationException;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.rotation.AdapterTokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.stereotype.Service;

@Service
public record TokenService(UserDao userDao, KeycloakDeployment keycloakDeployment) {

    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    /**
     * Verify incoming access token against authorization (keycloak) server and return user info.
     * If user is not registered in application, do registration.
     *
     * @param accessToken Incoming access token.
     * @return User info.
     * @throws AuthenticationException If user is not authenticated.
     */
    public UserShort verifyToken(String accessToken) throws AuthenticationException {
        try {
            if (accessToken.startsWith(BEARER_TOKEN_PREFIX)) {
                accessToken = accessToken.substring(BEARER_TOKEN_PREFIX.length());
            }
            AccessToken at = AdapterTokenVerifier.verifyToken(accessToken, keycloakDeployment);
            if (userDao.existsByUuid(at.getSubject())) {
                return new UserShort(at.getSubject());
            } else {
                return registerUser(at);
            }
        } catch (VerificationException e) {
            throw new AuthenticationException(e);
        }
    }

    private UserShort registerUser(AccessToken accessToken) {
        User user = User.builder().uuid(accessToken.getSubject()).build();
        user = userDao.save(user);
        return new UserShort(user);
    }

}
