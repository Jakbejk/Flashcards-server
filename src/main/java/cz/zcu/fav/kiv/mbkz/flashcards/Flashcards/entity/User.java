package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.constant.EntitiesNameRegistry;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;

@Entity
@Table(name = EntitiesNameRegistry.USER_TABLE)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractEntity {

    @Column(name = EntitiesNameRegistry.UUID_COLUMN, unique = true)
    private String uuid;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = EntitiesNameRegistry.SET_USER_JOIN_TABLE, joinColumns = @JoinColumn(name = EntitiesNameRegistry.USER_SET_FK), inverseJoinColumns = @JoinColumn(name = EntitiesNameRegistry.SET_USER_FK))
    private java.util.Set<Set> setList = new HashSet<>();

    public void addSet(Set set) {
        if (set != null) {
            if (this.getSetList() != null) {
                this.getSetList().add(set);
            }
            if (set.getUserList() != null) {
                set.getUserList().add(this);
            }
        }
    }

    public void removeSet(Set set) {
        if (set != null) {
            if (this.getSetList() != null) {
                this.getSetList().remove(set);
            }
            if (set.getUserList() != null) {
                set.getUserList().remove(this);
            }
        }
    }

}
