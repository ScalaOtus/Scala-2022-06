package hwCollect
import scala.util.Random

object Collects extends App {

  val urn = List(1,0,1,0,1,0)

  def arr(items: List[Int]): Boolean = items match {
    case head +: tail => if (items(Random.nextInt(items.length)) != head && tail(Random.nextInt(tail.length)) == 1) true else false
  }

  def timesRepeat(block: => Boolean)(n: Int): Double = {
    var acc: Double = 0
    (1 to n) foreach {_ =>
      if (block) {
        acc += 1
      }
    }
    acc
  }

  val end = timesRepeat(arr(urn))(10)
  println(end)
}
