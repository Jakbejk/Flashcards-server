package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dto.flashcard;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dto.AbstractDtoIn;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.Flashcard;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveDtoIn extends AbstractDtoIn {

    @NotNull
    private Flashcard flashcard;

}
