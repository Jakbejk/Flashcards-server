package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dto.set;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.Set;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetSaveDtoIn {

    @NotNull
    private Set set;

}
