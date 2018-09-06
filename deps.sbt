libraryDependencies += "org.typelevel" %% "cats-core" % "1.3.1"

val poiVersion = "4.0.0"

libraryDependencies ++= {
  Seq(
    //poi
    "org.apache.poi" % "poi" % poiVersion exclude("stax", "stax-api"),
    "org.apache.poi" % "poi-ooxml" % poiVersion exclude("stax", "stax-api"),
    "org.apache.poi" % "poi-ooxml-schemas" % poiVersion exclude("stax", "stax-api")
  )
}

libraryDependencies += "org.apache.commons" % "commons-math3" % "3.6.1"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.6-SNAP2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.6-SNAP2" % "test"

libraryDependencies += "org.scala-lang.modules" %% "scala-collection-compat" % "0.1.1"