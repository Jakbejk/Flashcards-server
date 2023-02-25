package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.controller;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dto.flashcard.SaveDtoIn;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dto.flashcard.SaveDtoOut;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.AuthenticationException;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.service.FlashcardService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flashcard/flashcard")
@RequiredArgsConstructor
public class FlashcardController {

    private final FlashcardService flashcardService;

    @PostMapping("/save")
    public SaveDtoOut save(@RequestBody SaveDtoIn dtoIn, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return flashcardService.save(dtoIn, token);
    }

    @GetMapping("/clear")
    public void clear(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws AuthenticationException {
        flashcardService.clear(token);
    }
}
