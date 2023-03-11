package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.service;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dao.UserDao;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.shadow.UserShort;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.AuthenticationException;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.rotation.AdapterTokenVerifier;
import org.keycloak.representations.AccessToken;
import org.springframework.stereotype.Service;

@Service
public record TokenService(UserDao userDao, KeycloakDeployment keycloakDeployment) {

    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    public UserShort verifyToken(String accessToken) {
        return verifyToken(accessToken, true);
    }

    /**
     * Verify incoming access token against authorization (keycloak) server and return user info.
     *
     * @param accessToken     Incoming access token.
     * @param verifyIntegrity If true, returned user is checked with database.
     * @return User info.
     * @throws AuthenticationException If user is not authenticated.
     */
    public UserShort verifyToken(String accessToken, boolean verifyIntegrity) throws AuthenticationException {
        try {
            if (accessToken.startsWith(BEARER_TOKEN_PREFIX)) {
                accessToken = accessToken.substring(BEARER_TOKEN_PREFIX.length());
            }
            AccessToken at = AdapterTokenVerifier.verifyToken(accessToken, keycloakDeployment);
            if (!verifyIntegrity) {
                return new UserShort(at.getSubject());
            } else if (userDao.existsByUuid(at.getSubject())) {
                return new UserShort(at.getSubject());
            }
            throw new AuthenticationException("User does not exists.");
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }
    }

}
