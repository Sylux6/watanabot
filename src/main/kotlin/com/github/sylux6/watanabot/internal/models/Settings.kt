package com.github.sylux6.watanabot.internal.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(name = "settings", uniqueConstraints = [UniqueConstraint(columnNames = ["guildId"])])
class Settings {

    @Id
    @GeneratedValue
    var id: Int? = null

    @Column
    var guildId: Long? = null

    @Column
    var birthdayChannelId: Long? = null

    constructor()

    constructor(guildId: Long) {
        this.guildId = guildId
    }

    constructor(guildId: Long, birthdayChannelId: Long?) {
        this.guildId = guildId
        this.birthdayChannelId = birthdayChannelId
    }
}
