import Database.setupDB
import scalikejdbc._

trait Database {

  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"
  val CClose =   "jdbc:derby:;shutdown=true";

  val dbURL = "jdbc:derby:myDB;create=true;";
  // initialize JDBC driver & connection pool
  Class.forName(derbyDriverClassname)

  ConnectionPool.singleton(dbURL,"Kourosh","password")
  implicit val session = AutoSession

}

//Creates the database
object Database extends Database {
  def setupDB() = {
    if (!hasDBInitialize)
      initializeTable()
  }
  def hasDBInitialize : Boolean = {

    DB getTable "dictionary" match {
      case Some(x) => true
      case None => false
    }
  }

  //For Database
  def initializeTable(): Any = {
    DB autoCommit { implicit session =>
      sql"""
			create table dictionary (
			  word varchar(64),
        define varchar(64)
			)
			""".execute.apply()
    }

  }

  def addValues1(): Any = {
    (DB autoCommit { implicit session =>
      sql"""
					insert into dictionary(word,define) values('Chicken','a domestic fowl kept for its eggs or meat')
				""".update.apply()
    })
  }
  def addValues2(): Any = {
    (DB autoCommit { implicit session =>
      sql"""
					insert into dictionary(word,define) values('Book','a written work consisting of pages')
				""".update.apply()
    })
  }
  def addValues3(): Any = {
    (DB autoCommit { implicit session =>
      sql"""
					insert into dictionary(word,define) values('Coffee','A hot drink made from roasted beans')
				""".update.apply()
    })
  }

  def getDefine(wordS:String): List[String] ={
    DB readOnly{ implicit session =>
      sql"select * from dictionary where word = $wordS".map(rsx => new String(rsx.string("define"))).list.apply()
    }
  }

  def getWords(): List[String] ={
    DB readOnly{ implicit session =>
      sql"select * from dictionary".map(rs => new String(rs.string("word"))).list.apply()
    }
  }



}
