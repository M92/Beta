
package edu.chl.library.core;

import edu.chl.library.persistence.IDAO;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface to order book
 * @author hajo
 */
@Local
public interface IUser extends IDAO<Movie, Long> {

    List<Movie> getByUser(User c);
    
}
