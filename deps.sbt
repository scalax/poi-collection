libraryDependencies += "org.typelevel" %% "cats-core" % "1.1.0"

val poiVersion = "3.17"

libraryDependencies ++= {
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

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"