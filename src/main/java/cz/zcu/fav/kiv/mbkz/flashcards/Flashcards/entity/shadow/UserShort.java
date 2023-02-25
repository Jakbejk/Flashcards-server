package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.shadow;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dao.UserDao;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.User;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.enums.RequestSource;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserShort {

    private String uuid;
    private RequestSource provider;

    private String email;

    private Boolean emailVerified;

    public UserShort(User user) {
        if (user != null) {
            this.uuid = user.getUuid();
            this.provider = user.getProvider();
            this.email = user.getEmail();
            this.emailVerified = user.getEmailVerified();
        }
    }



}
