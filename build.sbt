organization := "org.xarcher"

name := "poi-collection"

version := "0.1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= {
  val poiVersion = "3.13"
  Seq(
    //poi
    "org.apache.poi" % "poi" % poiVersion exclude("stax", "stax-api"),
    "org.apache.poi" % "poi-ooxml" % poiVersion exclude("stax", "stax-api"),
    "org.apache.poi" % "poi-ooxml-schemas" % poiVersion exclude("stax", "stax-api"),
    
    //joda-time
    "joda-time" % "joda-time" % "2.9.1",
    "org.joda" % "joda-convert" % "1.7",
    
    "org.scala-lang" % "scala-reflect" % scalaVersion.value
  )
}