package db.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "member", uniqueConstraints = {@UniqueConstraint(columnNames={"userId", "guildId"})})
public class Member {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private Long userId;

    @Column
    private Long guildId;

    @Column
    private Date birthday;

    public Member() {
    }

    public Member(Long userId, Long guildId) {
        this.userId = userId;
        this.guildId = guildId;
    }

    public Member(Long userId, Long guildId, Date birthday) {
        this.userId = userId;
        this.guildId = guildId;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public long getGuildId() {
        return guildId;
    }

    public void setGuildId(Long guildId) {
        this.guildId = guildId;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
