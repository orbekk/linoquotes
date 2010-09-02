package lq;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Vote {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private Quote quote;

    @Persistent
    private Date timestamp;

    @Persistent
    private String ip;

    public Vote(Quote quote, String ip) {
        this.quote = quote;
        this.ip = ip;
        timestamp = new Date();
    }

    public Long getId() { return id; }
    public Quote getQuote() { return quote; }
    public Date getTimestamp() { return timestamp; }
    public String getIp() { return ip; }

    public void setId(Long id) { this.id = id; }
    public void setQuote(Quote quote) { this.quote = quote; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
    public void setIp(String ip) { this.ip = ip; }
}
