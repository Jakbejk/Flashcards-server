package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dto.set;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SetTransferDtoIn {
    @NotNull
    @Min(1)
    private Long setId;
    @NotBlank
    private String userUuid;

}
