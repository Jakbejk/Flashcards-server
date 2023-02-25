package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.controller;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.component.token.GoogleTokenValidator;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dao.UserDao;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.User;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.shadow.UserShort;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.enums.RequestSource;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.AuthenticationException;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
@RequestMapping("/flashcard/token")
@RequiredArgsConstructor
public class TokenController {

    private final GoogleTokenValidator googleTokenValidator;
    private final TokenService tokenService;

    private final UserDao userDao;

    @GetMapping("/google/login")
    public String loginViaGoogle(@RequestHeader(HttpHeaders.AUTHORIZATION) String idToken) throws AuthenticationException {
        // HDS 1.0 - Validate token
        // ERR 1.0 - Token is not valid
        UserShort userShort = googleTokenValidator.validateToken(idToken);
        // HDS 2.0 - Create and return token
        // ERR 2.0 - Token can not be created
        try {
            return tokenService.createToken(userShort);
        } catch (Exception e) {
            throw new AuthenticationException("Token can not be created.", e);
        }
    }

    @GetMapping("/google/register")
    public String registerViaGoogle(@RequestHeader(HttpHeaders.AUTHORIZATION) String idToken) throws AuthenticationException {
        // HDS 1.0 - Validate token
        // ERR 1.0 - Token is not valid
        UserShort userShort = googleTokenValidator.validateTokenLocal(idToken);
        // HDS 2.0 - Check user registration state
        if (userDao.findUserShortByUuid(userShort.getUuid()) == null) {
            // HDS 2.1 - Register user
            User user = User.builder().uuid(userShort.getUuid()).email(userShort.getEmail()).emailVerified(userShort.getEmailVerified()).provider(RequestSource.GOOGLE).setList(new HashSet<>()).build();
            user = userDao.save(user);
            userShort = new UserShort(user);
        }
        // HDS 3.0 - Create and return token
        // ERR 2.0 - Token can not be created
        try {
            return tokenService.createToken(userShort);
        } catch (Exception e) {
            throw new AuthenticationException("Token can not be created.", e);
        }
    }
}
