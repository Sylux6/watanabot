package db.config

import com.improve_future.harmonica.core.DbConfig
import com.improve_future.harmonica.core.Dbms
import db.utils.DB_HOST
import db.utils.DB_NAME
import db.utils.DB_PASSWORD
import db.utils.DB_PORT
import db.utils.DB_USER

class Default : DbConfig({
    dbms = Dbms.PostgreSQL
    dbName = DB_NAME
    host = DB_HOST
    port = DB_PORT
    user = DB_USER
    password = DB_PASSWORD
})
