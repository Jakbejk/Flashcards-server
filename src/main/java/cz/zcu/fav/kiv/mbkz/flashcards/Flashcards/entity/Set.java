package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.constant.EntitiesNameRegistry;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;

@Entity
@Table(name = EntitiesNameRegistry.SET_TABLE)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Set extends AbstractEntity {

    @Column(name = EntitiesNameRegistry.SET_NAME_COLUMN)
    @NotBlank
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = EntitiesNameRegistry.ID_COLUMN)
    private java.util.Set<Flashcard> flashcardList = new HashSet<>();


    @ManyToMany(mappedBy = "setList")
    @JsonIgnore
    private java.util.Set<User> userList = new HashSet<>();

    public void addUser(User user) {
        if (user != null) {
            if (this.getUserList() != null) {
                this.getUserList().add(user);
            }
            if (user.getSetList() != null) {
                user.getSetList().add(this);
            }
        }
    }

    public void removeUser(User user) {
        if (user != null) {
            if (this.getUserList() != null) {
                this.getUserList().remove(user);
            }
            if (user.getSetList() != null) {
                user.getSetList().remove(this);
            }
        }
    }
}
