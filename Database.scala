import scalafx.collections.ObservableBuffer
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

  def hasDBInitialize: Boolean = {

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

  def getDefine(wordS: String): List[String] = {
    DB readOnly { implicit session =>
      sql"select * from dictionary where word = $wordS".map(rsx => new String(rsx.string("define"))).list.apply()
    }
  }

  def getWords(): List[String] = {
    DB readOnly { implicit session =>
      sql"select * from dictionary".map(rs => new String(rs.string("word"))).list.apply()
    }
  }

  setupDB()


  var word1:Dictionary = new Dictionary("Chicken","a domestic fowl kept for its eggs or meat")
  var word2:Dictionary = new Dictionary("Book","a written work consisting of pages")
  var word3:Dictionary = new Dictionary("Coffee","A hot drink made from roasted beans")
  var word4:Dictionary = new Dictionary("Tea","a hot drink made by infusing leaves of the tea")
  var word5:Dictionary = new Dictionary("Candy","sweets; confectionery.")
  var word6:Dictionary = new Dictionary("Mint","a peppermint sweet.")
  var word7:Dictionary = new Dictionary("Chocolate","a sweet made of or covered with chocolate.")
  var word8:Dictionary = new Dictionary("Gum","a gum tree, especially a eucalyptus")
  var word9:Dictionary = new Dictionary("Tissue","tissue paper.")
  var word10:Dictionary = new Dictionary("Paper","a sheet of paper with something written or printed on it")
  var word11:Dictionary = new Dictionary("Cotton","a soft white fibrous substance")
  var word12:Dictionary = new Dictionary("Life","the existence of an individual human being or animal")

  val wordData = new ObservableBuffer[Dictionary]
  wordData += word1
  wordData += word2
  wordData += word3
  wordData += word4
  wordData += word5
  wordData += word6
  wordData += word7
  wordData += word8
  wordData += word9
  wordData += word10
  wordData += word11
  wordData += word12

  for(word <- wordData) {
    word.save()
  }
}




