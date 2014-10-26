package dat076.group4.model.dao;

import dat076.group4.model.core.User;
import dat076.group4.model.persistence.IDAO;

import java.util.List;
import javax.ejb.Local;

/**
 * Interface to user registry
 */
@Local
public interface IUserRegistry extends IDAO<User, Long> {
    User getByOAuth (Long oath);
    User getByNickname(String nickname);
}
