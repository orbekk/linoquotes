package lq;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Quote {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private Date timestamp;

    @Persistent
    private Boolean approved;

    @Persistent
    private Date quoteDate;

    @Persistent
    private String author;

    @Persistent
    private String content;

    @Persistent
    private String ip;

    public Quote(Date quoteDate, String author, String content, String ip) {
        this.quoteDate = quoteDate;
        this.author = author;
        this.content = content;
        this.ip = ip;

        this.timestamp = new Date();
    }

    public Long getId() { return id; }
    public Date getTimestamp() { return timestamp; }
    public Boolean getApproved() { return approved; }
    public Date getQuoteDate() { return quoteDate; }
    public String getAuthor() { return author; }
    public String getContent() { return content; }
    public String getIp() { return ip; }

    public void setId(Long id) { this.id = id; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
    public void setApproved(Boolean approved) { this.approved = approved; }
    public void setQuoteDate(Date quoteDate) { this.quoteDate = quoteDate; }
    public void setAuthor(String author) { this.author = author; }
    public void setContent(String content) { this.content = content; }
    public void setIp(String ip) { this.ip = ip; }
}
