//bintrayOrganization := Some("scalax")
//bintrayRepository := "poi-collection"
licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

resolvers += Resolver.jcenterRepo

resolvers += Resolver.bintrayRepo("djx314", "maven")

bintrayPackageLabels := Seq("scala", "poi")

organization := "net.scalax"

name := "poi-collection"

scalaVersion := "2.13.1"

crossScalaVersions := Seq("2.13.1", "2.12.10")

scalacOptions ++= Seq("-feature", "-deprecation")
