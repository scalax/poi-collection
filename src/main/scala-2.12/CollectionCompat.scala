package net.scalax.cpoi.utils.compat

import scala.collection.compat._

object CollectionCompat {

  type LazyList[T] = scala.collection.immutable.Stream[T]
  def seqToLazyList[T](seq: Seq[T]): LazyList[T] = seq.toStream

  def mapFromMutable[T1, T2](map: scala.collection.mutable.Map[T1, T2]): Map[T1, T2]   = Map.from(map)
  def mapFromImmutable[T1, T2](map: Map[T1, T2]): scala.collection.mutable.Map[T1, T2] = scala.collection.mutable.Map.from(map)

}
