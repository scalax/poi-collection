organization := "net.scalax"

name := "poi-collection"

version := "0.1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= {
  val poiVersion = "3.15-beta1"
  Seq(
    //poi
    "org.apache.poi" % "poi" % poiVersion exclude("stax", "stax-api"),
    "org.apache.poi" % "poi-ooxml" % poiVersion exclude("stax", "stax-api"),
    "org.apache.poi" % "poi-ooxml-schemas" % poiVersion exclude("stax", "stax-api"),
    
    //joda-time
    "joda-time" % "joda-time" % "2.9.4",
    "org.joda" % "joda-convert" % "1.8.1",
    
    "org.scala-lang" % "scala-reflect" % scalaVersion.value
  )
}
