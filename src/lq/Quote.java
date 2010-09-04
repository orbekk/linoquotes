package lq;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
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
    private Text content;

    @Persistent
    private String ip;

    @Persistent
    private Double sumVotes;

    @Persistent
    private Integer numVotes;

    @Persistent
    // sum [ (rating-3) * abs((rating-3)) | rating <- votes ]
    private Double scorePoints;

    public Quote(Date quoteDate, String author, String content, String ip) {
        this.quoteDate = quoteDate;
        this.author = author;
        this.content = new Text(content);
        this.ip = ip;
        this.sumVotes = 0.0;
        this.numVotes = 0;
        this.scorePoints = 0.0;
        this.timestamp = new Date();
    }

    public Double getScore() {
        return (getNumVotes() > 0.0 ? getSumVotes()/getNumVotes() : 0.0);
    }

    public Long getId() { return id; }
    public Date getTimestamp() { return timestamp; }
    public Boolean getApproved() { return approved; }
    public Date getQuoteDate() { return quoteDate; }
    public String getAuthor() { return author; }
    public String getContent() { return content.getValue(); }
    public String getIp() { return ip; }
    public Double getSumVotes() { return sumVotes; }
    public Integer getNumVotes() { return numVotes; }
    public Double getScorePoints() { return scorePoints; }

    public void setId(Long id) { this.id = id; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
    public void setApproved(Boolean approved) { this.approved = approved; }
    public void setQuoteDate(Date quoteDate) { this.quoteDate = quoteDate; }
    public void setAuthor(String author) { this.author = author; }
    public void setContent(String content) { this.content = new Text(content); }
    public void setIp(String ip) { this.ip = ip; }
    public void setSumVotes(double sumVotes) { this.sumVotes = sumVotes; }
    public void setNumVotes(int numVotes) { this.numVotes = numVotes; }
    public void setScorePoints(double scorePoints) { this.scorePoints = scorePoints; }
}
