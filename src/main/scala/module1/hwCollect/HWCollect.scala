package module1.hwCollect

import scala.util.Random

object HWCollect extends App {

  val urn = List(0,1,1,0,0,1)

  def f1(items: List[Int]): Boolean = items match {
    case _ => if (items(Random.nextInt(items.length)) == 0)
      true
    else
      false
  }

  def f2(items: List[Int]): Boolean = items match {
    case _ +: tail => if (items(Random.nextInt(items.length)) == 0 && tail(Random.nextInt(tail.length)) == 1)
      true
    else
      false
  }

  def timesRepeat(f1: => Boolean)(f2: => Boolean)(n: Int): Double = {
    var r1: Double = 0
    var r2: Double = 0
    for(i<-1 to n) {
      if (f1) r1 += 1
      if (f2) r2 += 1
    }
    r2/r1
  }

  val r: Double = timesRepeat(f1(urn))(f2(urn))(100000)

  println(r)

}
