package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.constant.EntitiesNameRegistry;
import jakarta.persistence.*;

import java.util.Objects;

@MappedSuperclass
public class AbstractEntity {

    @Id
    @Column(name = EntitiesNameRegistry.ID_COLUMN)
    @GeneratedValue
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
