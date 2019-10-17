import scalikejdbc._

import scala.util.Try

class Dictionary(var wordS :String, var definitionS: String) {


  def save(): Try[Int] = {
    if (!(isExist)) {
      Try(DB autoCommit { implicit session =>
        sql"""
					insert into dictionary(word,define) values(${wordS},${definitionS})
				""".update.apply()
      })
    } else {
      Try(DB autoCommit { implicit session =>
        sql"""
				update dictionary
				set
				word  = ${wordS}
				AND define = ${definitionS},
				""".update.apply()
      })
    }
  }

  def isExist: Boolean = {
    DB readOnly { implicit session =>
      sql"""
				select * from dictionary where
				word = ${wordS} AND
        define = ${definitionS}
			""".map(rs => rs.string("word")).single.apply()
    } match {
      case Some(x) => true
      case None => false
    }

  }

  object Dictionary extends Database {
    def apply(
               wordS: String,
               definitionS: String
             ): Dictionary = new Dictionary(wordS, definitionS) {

    }
  }

}
