import java.io.{DataInputStream, DataOutputStream}
import java.net.{ServerSocket, Socket}

import Database.setupDB

import scala.concurrent.ExecutionContext.global
import scala.concurrent.Future

object Server extends App{

  var client: Socket = _

  var definitionS:String = _
  implicit val ex = global
  val server = new ServerSocket( 5000 )
  while(true) {
    client = server.accept()
    println(s"1")

    val f = Future({
      val is = new DataInputStream(client.getInputStream())
      val os = new DataOutputStream(client.getOutputStream())
      var line: String = is.readLine() //Waits for user to input to continue(Main thread is stopped otherwise)
      println(line)

      for(word <- (Database.getWords())){
        if( line == word ){
          println("TESTING")
          definitionS = Database.getDefine(line).head
          println(s"Server received $line")
          os.writeBytes(s"The definition of the word you entered is $definitionS\n") //\n Allows for progress to next step(It indicates end of line).Works with read line above
        }
      }

    }
    )

  }
  client.close()

}
