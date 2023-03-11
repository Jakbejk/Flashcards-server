package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.shadow;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserShort {

    private String uuid;

    public UserShort(User user) {
        if (user != null) {
            this.uuid = user.getUuid();
        }
    }

}
