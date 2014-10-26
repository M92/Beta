package dat076.group4.model.persistence;

import java.util.List;

/**
 * Basic contract for containers.
 *
 * @param <T> Type of items in container
 * @param <K> Primary key for items
 */
public interface IDAO<T, K> {

    void create(T t);

    void delete(K id);

    void update(T t);

    T find(K id);

    List<T> findAll();

    List<T> findRange(int first, int n );

    int count();
}
