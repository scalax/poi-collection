package net.scalax.cpoi.utils.compat

object CollectionCompat {

  type LazyList[T] = scala.collection.immutable.LazyList[T]
  def seqToLazyList[T](seq: Seq[T]): LazyList[T] = seq.to(LazyList)

  def mapFromMutable[T1, T2](map: scala.collection.mutable.Map[T1, T2]): Map[T1, T2]                              = map.to(Map)
  def mapFromImmutable[T1, T2](map: scala.collection.immutable.Map[T1, T2]): scala.collection.mutable.Map[T1, T2] = map.to(scala.collection.mutable.Map)

}
