package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.constant.TokenSignRegistry;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dao.UserDao;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.User;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.shadow.UserShort;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.AuthenticationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Service
public final class TokenService {

    private final RSAPrivateKey privateKey;

    private final RSAPublicKey publicKey;

    private final UserDao userDao;


    public TokenService(UserDao userDao) throws Exception {
        this.privateKey = generatePrivateKey();
        this.publicKey = generatePublicKey();
        this.userDao = userDao;
    }

    /**
     * Generates a private key of for signing tokens. Used only for initialization.
     *
     * @return Private key for signing the tokens
     * @throws Exception If any problem occurs
     */
    private static RSAPrivateKey generatePrivateKey() throws Exception {
        ClassPathResource resource = new ClassPathResource(TokenSignRegistry.PRIVATE_KEY_PATH);
        byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI().getPath()));

        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(TokenSignRegistry.ALGORITHM);
        return (RSAPrivateKey) kf.generatePrivate(spec);
    }

    /**
     * Generates a public key of for signing tokens. Used only for initialization.
     *
     * @return Public key for signing the tokens
     * @throws Exception If any problem occurs
     */
    private static RSAPublicKey generatePublicKey() throws Exception {
        ClassPathResource resource = new ClassPathResource(TokenSignRegistry.PUBLIC_KEY_PATH);
        byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI().getPath()));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(TokenSignRegistry.ALGORITHM);
        return (RSAPublicKey) kf.generatePublic(spec);
    }

    private static String separateBearerPref(String token) {
        if (StringUtils.isBlank(token)) {
            return token;
        }
        if (token.startsWith(TokenSignRegistry.BEARER_TOKEN_PREFIX + " ")) {
            return token.substring(TokenSignRegistry.BEARER_TOKEN_PREFIX.length() + 1);
        } else if (token.startsWith(TokenSignRegistry.BEARER_TOKEN_PREFIX)) {
            return token.substring(TokenSignRegistry.BEARER_TOKEN_PREFIX.length());
        }
        return token;
    }

    /**
     * Create signed token for current user and return it
     *
     * @param user User for which will be token generated
     * @return String as current token
     */
    public String createToken(User user) throws JWTCreationException {
        return createToken(new UserShort(user));
    }

    /**
     * Create signed token for current user and return it
     *
     * @param userShort User for which will be token generated
     * @return String as current token
     */
    public String createToken(UserShort userShort) throws JWTCreationException {
        if (userShort == null) {
            throw new JWTCreationException("Token can not be created (user can not be null).", new Exception());
        }
        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
            return JWT.create().withIssuer(TokenSignRegistry.ISSUER_VALUE).withClaim(TokenSignRegistry.UUID, userShort.getUuid()).withClaim(TokenSignRegistry.PROVIDER, userShort.getProvider() == null ? "" : userShort.getProvider().name()).sign(algorithm);
        } catch (Exception exception) {
            throw new JWTCreationException("Token can not be created.", exception);
        }
    }

    /**
     * Verify signed token and return UserShort information. If token has invalid format or inconsistent info with database, method throws exception. Method never return null, if any problem occur, exception is thrown.
     *
     * @param token Token to be verified.
     * @return Information about verified user.
     * @throws AuthenticationException If any error in parsing occurs.
     */

    public UserShort verifyToken(String token) throws AuthenticationException {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(TokenSignRegistry.ISSUER_VALUE).build();
            String accessToken = separateBearerPref(token);
            decodedJWT = verifier.verify(accessToken);
            String uuid = decodedJWT.getClaim(TokenSignRegistry.UUID).asString();
            String provider = decodedJWT.getClaim(TokenSignRegistry.PROVIDER).asString();
            UserShort verifiedUser = userDao.findUserShortByUuid(uuid);
            if (verifiedUser == null) {
                throw new AuthenticationException("User does not exists (" + uuid + ") .");
            } else if (!StringUtils.equals(provider, verifiedUser.getProvider() == null ? null : verifiedUser.getProvider().name())) {
                throw new AuthenticationException("Invalid provider (" + provider + ") .");
            }
            return verifiedUser;
        } catch (Exception exception) {
            throw new AuthenticationException(exception);
        }
    }


}
