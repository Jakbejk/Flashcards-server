package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.constant.EntitiesNameRegistry;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = EntitiesNameRegistry.FLASHCARD_TABLE)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Flashcard extends AbstractEntity {

    @Column(name = EntitiesNameRegistry.QUESTION_COLUMN, nullable = false)
    @NotBlank
    private String question;

    @Column(name = EntitiesNameRegistry.IMAGE_COLUMN)
    private byte[] image;

    @Column(name = EntitiesNameRegistry.ANSWER_COLUMN, nullable = false)
    @NotBlank
    private String answer;
}
