import module1.opt.Option
import module1.list.{List, incList, shoutString}

val t: Option[String] = Option("abc")
val r: Option[Int] = Option(1)
val b: Option[Int] = Option(2)

t.printIfAny
t.zip(r)
r.filter((v: Int) => v == 1)
b.filter((v: Int) => v == 1)

val list = List(1, 2, 3)
val list2 = list.cons(4)
list2.mkString(";")
val rev = list2.reverse
rev.mkString(";")
list.map((v: Int) => v + 1)
val list3 = List(1, 2, 3, 4, 5)
list3.filter((v: Int) => v > 3)
incList(list3)
val list4 = List("str", "", "th")
shoutString(list4)
