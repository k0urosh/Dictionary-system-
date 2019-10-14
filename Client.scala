import java.io.{DataInputStream, DataOutputStream}
import java.net.Socket

object Client extends App {

  var client = new Socket( "127.0.0.1", 5000 )
  val is = new DataInputStream(client.getInputStream)
  val os = new DataOutputStream(client.getOutputStream)
  os.writeBytes(s"${scala.io.StdIn.readLine("Enter:")}\n")
  var line: String = is.readLine()
  println(s"The price is $line")
  client.close()
}
