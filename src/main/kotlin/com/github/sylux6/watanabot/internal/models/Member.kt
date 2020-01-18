package com.github.sylux6.watanabot.internal.models

import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(name = "member", uniqueConstraints = [UniqueConstraint(columnNames = ["userId", "guildId"])])
class Member {

    @Id
    @GeneratedValue
    private var id: Int? = null

    @Column
    private var userId: Long? = null

    @Column
    private var guildId: Long? = null

    @Column
    var birthday: Date? = null

    constructor()

    constructor(userId: Long, guildId: Long) {
        this.userId = userId
        this.guildId = guildId
    }

    constructor(userId: Long, guildId: Long, birthday: Date?) {
        this.userId = userId
        this.guildId = guildId
        this.birthday = birthday
    }

    fun getId(): Int {
        return id!!
    }

    fun getUserId(): Long {
        return userId!!
    }

    fun setId(id: Int?) {
        this.id = id
    }
}
