package edu.chl.library.core;

import edu.chl.library.persistence.AbstractEntity;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * A Customer
 * @author hajo
 */

//@Table(name="Cust", schema="RECORDS")
@Entity
public class User extends AbstractEntity {

    public User(){    
    }
    private String fname;
    
    private String lname;
    
    private String email;

    public User( String fname,
            String lname, String email) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
    }
    
    public User(Long id, String fname,
            String lname, String email) {
        super(id);
        this.fname = fname;
        this.lname = lname;
        this.email = email;
    }


    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + getId() + ", " + "fname=" + fname + ", lname=" + lname + ", email=" + email + '}';
    }
}
