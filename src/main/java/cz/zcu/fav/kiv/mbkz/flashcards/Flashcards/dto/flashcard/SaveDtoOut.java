package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dto.flashcard;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.Flashcard;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveDtoOut {

    private Flashcard flashcard;

}
