package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.service;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.component.validator.DtoInValidator;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dao.SetDao;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dao.UserDao;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dto.set.*;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.Set;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.User;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.shadow.UserShort;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.AuthenticationException;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.SetErrorRegistry;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.UserErrorRegistry;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.ValidationException;
import org.springframework.stereotype.Service;

@Service
public record SetService(SetDao setDao, TokenService tokenService, DtoInValidator dtoInValidator, UserDao userDao) {

    public SetSaveDtoOut save(SetSaveDtoIn dtoIn, String token) throws AuthenticationException, ValidationException {
        // HDS 1.0 - verify token
        // ERR 1.0 - token is invalid
        UserShort authUser = tokenService.verifyToken(token);
        // HDS 2.0 - validate dtoIn
        // ERR 2.0 - dtoIn has invalid format (required fields, etc.)
        dtoInValidator.validateDtoIn(dtoIn);
        // HDS 3.0 - find current user
        User user = userDao.findUserByUuid(authUser.getUuid());
        // HDS 4.0 - persist set into database
        Set set = setDao.save(dtoIn.getSet());
        // HDS 5.0 - establish relation between user and set
        {
            // HDS 5.1 - set already exists in relation - remove old record
            user.removeSet(set);
            // HDS 5.2 - establish new relation between user and set
            user.addSet(set);
            // HDS 5.3 - persist new relation
            userDao.save(user);
        }
        // HDS 6.0 - return response
        return new SetSaveDtoOut(set);
    }

    public SetGetDtoOut get(SetGetDtoIn dtoIn, String token) {
        // HDS 1.0 - verify token
        // ERR 1.0 - token is invalid
        UserShort authUser = tokenService.verifyToken(token);
        // HDS 2.0 - validate dtoIn
        // ERR 2.0 - dtoIn has invalid format (required fields, etc.)
        dtoInValidator.validateDtoIn(dtoIn);
        // HDS 3.0 - find current user
        User user = userDao.findUserByUuid(authUser.getUuid());
        // HDS 4.0 - find searched set
        Set set = setDao.findById(dtoIn.getId()).orElse(null);
        if (set == null) {
            // ERR 3.0 - set with given ID was not found
            throw new SetErrorRegistry.SetNotFoundException();
        } else if (user.getSetList() == null || !user.getSetList().contains(set)) {
            // ERR 4.0 - set is not accessible to current user
            throw new SetErrorRegistry.EnoughPermissionsForSet();
        } else {
            // HDS 5.0 - return response
            return new SetGetDtoOut(set);
        }
    }

    public void transfer(SetTransferDtoIn dtoIn, String token) {
        // HDS 1.0 - verify token
        // ERR 1.0 - token is invalid
        UserShort authUser = tokenService.verifyToken(token);
        // HDS 2.0 - validate dtoIn
        // ERR 2.0 - dtoIn has invalid format (required fields, etc.)
        dtoInValidator.validateDtoIn(dtoIn);
        // HDS 3.0 - find current user
        User currentUser = userDao.findUserByUuid(authUser.getUuid());
        // HDS 4.0 - load from DB according dtoIn
        Set set = setDao.findById(dtoIn.getSetId()).orElse(null);
        User user = userDao.findUserByUuid(dtoIn.getUserUuid());
        if (set == null) {
            // ERR 3.0 - set with given ID was not found
            throw new SetErrorRegistry.SetNotFoundException();
        } else if (user == null) {
            // ERR 4.0 - target user was not found
            throw new UserErrorRegistry.UserNotFoundException();
        } else if (!currentUser.getSetList().contains(set)) {
            // ERR 5.0 - set is not accessible to current user
            throw new SetErrorRegistry.EnoughPermissionsForSet();
        }
        // HDS 5.0 - persists new relation
        user.removeSet(set);
        user.addSet(set);
        userDao.save(user);
    }
}
