package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.component.token;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dao.UserDao;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.shadow.UserShort;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.enums.RequestSource;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleTokenValidator implements IAbstractTokenValidator {

    private final UserDao userDao;

    @Override
    public UserShort validateToken(String accessToken) throws AuthenticationException {
        try {
            HttpTransport httpTransport = new NetHttpTransport();
            JsonFactory jsonFactory = new GsonFactory();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory).build();
            GoogleIdToken idToken = verifier.verify(accessToken);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                if (payload != null) {
                    String uuid = payload.getSubject();
                    if (StringUtils.isAnyBlank(payload.getEmail(), uuid)) {
                        throw new AuthenticationException();
                    } else {
                        return userDao.findUserShortByUuid(uuid);
                    }
                } else {
                    throw new AuthenticationException();
                }
            } else {
                throw new AuthenticationException();
            }
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }
    }

    @Override
    public UserShort validateTokenLocal(String accessToken) throws AuthenticationException {
        try {
            HttpTransport httpTransport = new NetHttpTransport();
            JsonFactory jsonFactory = new GsonFactory();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory).build();
            GoogleIdToken idToken = verifier.verify(accessToken);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                if (payload != null) {
                    String uuid = payload.getSubject();
                    if (StringUtils.isAnyBlank(payload.getEmail(), uuid)) {
                        throw new AuthenticationException();
                    } else {
                        return UserShort.builder().uuid(payload.getSubject()).email(payload.getEmail()).emailVerified(payload.getEmailVerified()).provider(RequestSource.GOOGLE).build();
                    }
                } else {
                    throw new AuthenticationException();
                }
            } else {
                throw new AuthenticationException();
            }
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }
    }
}
