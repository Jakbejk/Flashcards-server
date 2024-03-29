package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.controller;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dto.set.*;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.AuthenticationException;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.exception.ValidationException;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.service.SetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flashcard/set")
@RequiredArgsConstructor
public class SetController {

    private final SetService setService;

    @PostMapping("/save")
    public SetSaveDtoOut save(@RequestBody SetSaveDtoIn dtoIn, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws AuthenticationException, ValidationException {
        return setService.save(dtoIn, token);
    }

    @GetMapping("/get")
    public SetGetDtoOut get(@RequestParam("id") Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return setService.get(new SetGetDtoIn(id), token);
    }

    @PostMapping("/transfer")
    public void transfer(@RequestBody SetTransferDtoIn dtoIn, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        setService.transfer(dtoIn, token);
    }

    @PostMapping("/listAllShort")
    public SetListAllDtoOut listAllShort(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return setService.listAllShort(token);
    }

}
