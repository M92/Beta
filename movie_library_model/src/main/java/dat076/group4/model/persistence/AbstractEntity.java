package dat076.group4.model.persistence;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Base class for all entities
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id private Long id;

    public long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractEntity other = (AbstractEntity)obj;
        return Objects.equals(id, other.getId());
    }  
}
