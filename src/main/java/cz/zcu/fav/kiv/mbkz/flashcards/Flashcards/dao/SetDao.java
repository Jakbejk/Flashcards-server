package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dao;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetDao extends CrudRepository<Set, Long> {
}
