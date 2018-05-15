//bintrayOrganization := Some("scalax")
//bintrayRepository := "poi-collection"
licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

resolvers += Resolver.jcenterRepo

resolvers += Resolver.bintrayRepo("djx314", "maven")

bintrayPackageLabels := Seq("scala", "poi")

organization := "net.scalax"

name := "poi-collection"

scalaVersion := "2.12.6"

crossScalaVersions := Seq("2.12.6", "2.11.12")

scalacOptions ++= Seq("-feature", "-deprecation", "-Ypartial-unification")