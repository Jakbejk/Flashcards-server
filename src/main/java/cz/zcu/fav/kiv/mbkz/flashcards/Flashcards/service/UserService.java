package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.service;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dao.UserDao;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.User;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.shadow.UserShort;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public record UserService(UserDao userDao, TokenService tokenService) {

    public void register(String token) throws AuthenticationException {
        // HDS 1.0 - verify token
        // ERR 1.0 - token is invalid
        UserShort authUser = tokenService.verifyToken(token, false);
        // HDS 2.0 - save user with given uuid
        if (!userDao.existsByUuid(authUser.getUuid())) {
            userDao.save(User.builder().uuid(authUser.getUuid()).build());
        }
    }

}
