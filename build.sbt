//bintrayOrganization := Some("scalax")
//bintrayRepository := "poi-collection"

resolvers += Resolver.jcenterRepo

resolvers += Resolver.bintrayRepo("djx314", "maven")

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
bintrayVcsUrl := Some("git@github.com:scalax/poi-collection.git")
bintrayPackageLabels := Seq("scala", "poi")
organization := "net.scalax"
name := "poi-collection"

scalaVersion := "2.13.1"
crossScalaVersions := Seq("2.13.1", "2.12.10")

scalacOptions ++= Seq("-feature", "-deprecation")
