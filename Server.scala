import java.io.{DataInputStream, DataOutputStream}
import java.net.{ServerSocket, Socket}

import scala.concurrent.ExecutionContext.global
import scala.concurrent.Future

object Server extends App{

  implicit val ex = global
  val server = new ServerSocket( 5000 )
  while(true) {
    val client: Socket = server.accept()

    val f = Future({
      val is = new DataInputStream(client.getInputStream())
      val os = new DataOutputStream(client.getOutputStream())
      var line: String = is.readLine() //Waits for user to input to continue(Main thread is stopped otherwise)
      val result = line match{//Add in database for proper marks
        case "apple" => 20.0
        case "orange" => 10.0
        case _ => 0.0
      }
      println(s"Server received $line")
      os.writeBytes(s"$result\n") //\n Allows for progress to next step(It indicates end of line).Works with read line above
      client.close()
    }
    )
  }
}
