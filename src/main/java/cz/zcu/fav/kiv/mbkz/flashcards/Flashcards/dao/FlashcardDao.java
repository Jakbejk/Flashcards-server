package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dao;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.Flashcard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashcardDao extends CrudRepository<Flashcard, Long> {
}
