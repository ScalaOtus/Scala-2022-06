package hwCollect
import scala.util.Random

object Collects extends App {

  val urn = List(0,1,0,1,0,1)

  def f1(items: List[Int]): Boolean = items match {
    case head +: _ => if (items(Random.nextInt(items.length)) == head) true else false
  }

  def f2(items: List[Int]): Boolean = items match {
    case head +: tail => if (items(Random.nextInt(items.length)) == head && tail(Random.nextInt(tail.length)) == 1) true else false
  }

  def timesRepeat(f1: => Boolean)(f2: => Boolean)(n: Int): Double = {
    var r1: Double = 0
    var r2: Double = 0
    (1 to n) foreach { _ => if (f1) r1 += 1}
    (1 to n) foreach { _ => if (f2) r2 += 1}
    r2/r1
  }

  val r = timesRepeat(f1(urn))(f2(urn))(100000)

  println(r)
}
