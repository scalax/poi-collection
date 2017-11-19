organization := "net.scalax"

name := "poi-collection"

scalaVersion := "2.12.4"

libraryDependencies ++= {
  val poiVersion = "3.17"
  Seq(
    //poi
    "org.apache.poi" % "poi" % poiVersion exclude("stax", "stax-api"),
    "org.apache.poi" % "poi-ooxml" % poiVersion exclude("stax", "stax-api"),
    "org.apache.poi" % "poi-ooxml-schemas" % poiVersion exclude("stax", "stax-api"),
    
    //joda-time
    "joda-time" % "joda-time" % "2.9.9",
    "org.joda" % "joda-convert" % "1.9.2"
  )
}

scalacOptions ++= Seq("-feature", "-deprecation")