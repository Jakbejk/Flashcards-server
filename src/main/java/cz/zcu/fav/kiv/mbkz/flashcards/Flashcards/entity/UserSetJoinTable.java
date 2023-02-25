package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.constant.EntitiesNameRegistry;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserSetJoinTable implements Serializable {

    @Column(name = EntitiesNameRegistry.SET_USER_FK)
    Long setId;

    @Column(name = EntitiesNameRegistry.USER_SET_FK)
    Long userId;

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }
        if (obj == null) {
            return false;
        } else if (obj instanceof UserSetJoinTable other) {
            return Objects.equals(this.userId, other.userId) && Objects.equals(this.setId, other.setId);
        } else {
            return false;
        }
    }
}
