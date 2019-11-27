libraryDependencies += "org.typelevel" %% "cats-core" % "2.1.0-RC2"

val poiVersion = "4.1.1"

libraryDependencies ++= {
  Seq(
    //poi
    "org.apache.poi" % "poi" % poiVersion exclude("stax", "stax-api"),
    "org.apache.poi" % "poi-ooxml" % poiVersion exclude("stax", "stax-api"),
    "org.apache.poi" % "poi-ooxml-schemas" % poiVersion exclude("stax", "stax-api")
  )
}

libraryDependencies += "org.apache.commons" % "commons-math3" % "3.6.1"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.8"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"

libraryDependencies += "org.scala-lang.modules" %% "scala-collection-compat" % "2.1.2"