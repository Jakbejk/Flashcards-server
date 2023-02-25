package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dao;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.User;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.shadow.UserShort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {

    User findUserByEmail(String email);

    @Query("SELECT new cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.shadow.UserShort(u) FROM User u WHERE u.uuid=:uuid")
    UserShort findUserShortByUuid(String uuid);

    User findUserByUuid(String uuid);

}
