package module1

object App {
  def main(args: Array[String]): Unit = {
//    def sumItUp: Int = {
//      def one(x: Int): Int = { return x; 1 }
//      val two = (x: Int) => { return x; 2 }
//      1 + one(2) + two(11)
//    }
//
//    println(sumItUp)
//
//    println("Hello world")
    val list = module1.list.List.to(1-2)
    val list2 = module1.list.List(1)
    val list3 = list2.cons(2)
    println(list)
  }
}
