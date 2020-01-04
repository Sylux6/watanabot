package com.github.sylux6.watanabot.utils

import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder

object DBUtils {

    private val registry = StandardServiceRegistryBuilder().configure("hibernate_cfg.xml").build()
    private val sessionFactory = MetadataSources(DBUtils.registry).buildMetadata().buildSessionFactory()


    fun insert(`object`: Any) {
        val session = sessionFactory.openSession()

        try {
            val tx = session.beginTransaction()
            session.persist(`object`)
            tx.commit()
        } catch (e: Exception) {
            // log
        }

    }

    fun saveOrUpdate(`object`: Any) {
        val session = sessionFactory.openSession()

        try {
            val tx = session.beginTransaction()
            session.saveOrUpdate(`object`)
            tx.commit()
        } catch (e: Exception) {
            // log
        }

    }

    fun delete(`object`: Any) {
        val session = sessionFactory.openSession()

        try {
            val tx = session.beginTransaction()
            session.delete(`object`)
            tx.commit()
        } catch (e: Exception) {
            // log
        }

    }

    fun selectAll(table: Class<*>): List<Any> {
        val session = sessionFactory.openSession()
        val hql = "FROM " + table.name
        val query = session.createQuery(hql)
        val result = query.resultList
        session.close()
        return result
    }

    fun query(sql: String): List<Any> {
        val session = sessionFactory.openSession()
        val query = session.createNativeQuery(sql)
        val result = query.resultList
        session.close()
        return result
    }

}
