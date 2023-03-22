package cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.dao;

import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.User;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.shadow.SetShort;
import cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.shadow.UserShort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDao extends CrudRepository<User, Long> {


    @Query("SELECT new cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.shadow.UserShort(u) FROM User u WHERE u.uuid=:uuid")
    UserShort findUserShortByUuid(String uuid);

    User findUserByUuid(String uuid);

    boolean existsByUuid(String uuid);

    @Query("SELECT new cz.zcu.fav.kiv.mbkz.flashcards.Flashcards.entity.shadow.SetShort(s.id, s.name) FROM User u JOIN u.setList s WHERE u.uuid=:uuid")
    List<SetShort> findUserSetShortListByUuid(String uuid);
}
