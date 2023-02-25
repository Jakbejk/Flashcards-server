package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dto.set;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SetGetDtoIn {

    @NotNull
    @Min(1)
    private Long id;

}
