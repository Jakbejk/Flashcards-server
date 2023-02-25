package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.service;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.component.validator.DtoInValidator;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dao.SetDao;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dao.UserDao;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dto.set.SetSaveDtoIn;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dto.set.SetSaveDtoOut;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.Set;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.User;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.shadow.UserShort;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.AuthenticationException;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SetService {

    private final SetDao setDao;
    private final TokenService tokenService;
    private final DtoInValidator dtoInValidator;
    private final UserDao userDao;

    public SetSaveDtoOut save(SetSaveDtoIn dtoIn, String token) throws AuthenticationException, ValidationException {
        // HDS 1.0 - verify token
        // ERR 1.0 - token is invalid
        UserShort authUser = tokenService.verifyToken(token);
        // HDS 2.0 - validate dtoIn
        // ERR 2.0 - dtoIn has invalid format (required fields, etc.)
        dtoInValidator.validateDtoIn(dtoIn);
        // HDS 3.0 - find current user and establish relation with set
        User user = userDao.findUserByUuid(authUser.getUuid());
        Set set = setDao.save(dtoIn.getSet());
        // HDS 3.1 - set already exists in relation - remove old record
        user.removeSet(set);
        // HDS 3.3 - establish new relation between user and set
        user.addSet(set);
        userDao.save(user);
        // HDS 4.0 - return response
        return new SetSaveDtoOut(set);
    }
}
