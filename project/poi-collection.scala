import sbt._
import Keys._
import cn.gov.heshan.sbt.CustomSettings
import com.typesafe.sbt.SbtGit._

object `poi-collection` extends Build {
  
  val initPrintln = """
               _                     _  _              _    _               
 _ __    ___  (_)         ___  ___  | || |  ___   ___ | |_ (_)  ___   _ __  
| '_ \  / _ \ | | _____  / __|/ _ \ | || | / _ \ / __|| __|| | / _ \ | '_ \ 
| |_) || (_) || ||_____|| (__| (_) || || ||  __/| (__ | |_ | || (_) || | | |
| .__/  \___/ |_|        \___|\___/ |_||_| \___| \___| \__||_| \___/ |_| |_|
"""
  println(initPrintln)

  lazy val `poi-collection` = (project in file("."))
  //common settings
  .settings(CustomSettings.customSettings: _*)
  .settings(
    name := "poi-collection",
    version := "0.0.1",
    libraryDependencies ++= Seq(
      //poi
      "org.apache.poi" % "poi" % "3.13" exclude("stax", "stax-api"),
      "org.apache.poi" % "poi-ooxml" % "3.13" exclude("stax", "stax-api"),
      "org.apache.poi" % "poi-ooxml-schemas" % "3.13" exclude("stax", "stax-api"),
      "joda-time" % "joda-time" % "2.8.2",
      "org.joda" % "joda-convert" % "1.7"
    )
  )

}
