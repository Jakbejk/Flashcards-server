package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dto.set;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.shadow.SetShort;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SetListAllDtoOut {
    private List<SetShort> setList;
}
