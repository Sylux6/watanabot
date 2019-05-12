package db.model;

import javax.persistence.*;

@Entity
@Table(name = "settings", uniqueConstraints = {@UniqueConstraint(columnNames={"guildId"})})
public class Settings {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private Long guildId;

    @Column
    private Long birthdayChannelId;

    public Settings() {
    }

    public Settings(Long guildId) {
        this.guildId = guildId;
    }

    public Settings(Long guildId, Long birthdayChannelId) {
        this.guildId = guildId;
        this.birthdayChannelId = birthdayChannelId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getGuildId() {
        return guildId;
    }

    public void setGuildId(Long guildId) {
        this.guildId = guildId;
    }

    public Long getBirthdayChannelId() {
        return birthdayChannelId;
    }

    public void setBirthdayChannelId(Long birthdayChannelId) {
        this.birthdayChannelId = birthdayChannelId;
    }
}
