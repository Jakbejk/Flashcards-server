package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.service;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.component.validator.DtoInValidator;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dao.FlashcardDao;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dto.flashcard.SaveDtoIn;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dto.flashcard.SaveDtoOut;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.Flashcard;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class FlashcardService {

    private final FlashcardDao flashcardDao;

    private final TokenService tokenService;

    private final DtoInValidator dtoInValidator;

    public void clear(String token) throws AuthenticationException {
        // HDS 1.0 - verify token
        // ERR 1.0 - token is invalid
        tokenService.verifyToken(token);
        // HDS 2.0 - clear all flashcards
        flashcardDao.deleteAll();
    }

    public SaveDtoOut save(SaveDtoIn saveDtoIn, String token) throws AuthenticationException {
        // HDS 1.0 - verify token
        // ERR 1.0 - token is invalid
        tokenService.verifyToken(token);
        // HDS 2.0 - validate dtoIn
        // ERR 2.0 - dtoIn has invalid format (required fields, etc.)
        dtoInValidator.validateDtoIn(saveDtoIn);
        // HDS 3.0 - save flashcard into database
        Flashcard flashcard = flashcardDao.save(saveDtoIn.getFlashcard());
        // HDS 4.0 - return response
        return SaveDtoOut.builder().flashcard(flashcard).build();
    }
}
