import java.io.{DataInputStream, DataOutputStream}
import java.net.Socket

import Database.setupDB

object Client extends App  {
  Database.addValues1()
  Database.addValues2()
  Database.addValues3()


  var client = new Socket( "127.0.0.1", 5000 )
  val is = new DataInputStream(client.getInputStream)
  val os = new DataOutputStream(client.getOutputStream)

  var decision: String = _
  do{
    //Menu
    println("Hello, please specify what action you wish to take. Type 1 for dictionary, 2 to exit")
    decision = readLine()

    if( decision == "2"){
      System.exit(1)
    }
    else if ( decision != "1" && decision != "2"){
      println("Please choose only one of the two options, 1 or 2")
    }


    os.writeBytes(s"${scala.io.StdIn.readLine("Enter:")}\n")
    var line: String = is.readLine()

    println(s"The definition of the word you entered is $line")

  }
  while (  decision == "1" )

  client.close()

}

