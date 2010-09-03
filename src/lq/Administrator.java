package lq;

import com.google.appengine.api.datastore.Key;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Administrator {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String email;

    public Administrator(String email) {
        this.email = email;
    }

    public Key getKey() { return key; }
    public String getEmail() { return email; }

    public void setKey(Key key) { this.key = key; }
    public void setEmail(String email) { this.email = email; }
}
