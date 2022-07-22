package module1

import module1.list.List.::

import java.util.UUID
import scala.annotation.tailrec
import java.time.Instant
import scala.collection.immutable.{AbstractSeq, LinearSeq}
import scala.language.postfixOps



/**
 * referential transparency
 */


 object referential_transparency{

  case class Abiturient(id: String, email: String, fio: String)

  type Html = String

  sealed trait Notification

  object Notification{
    case class Email(email: String, text: Html) extends Notification
    case class Sms(telephone: String, msg: String) extends Notification
  }


  case class AbiturientDTO(email: String, fio: String, password: String)

  trait NotificationService{
    def sendNotification(notification: Notification): Unit
    def createNotification(abiturient: Abiturient): Notification
  }


  trait AbiturientService{

    def registerAbiturient(abiturientDTO: AbiturientDTO): Abiturient
  }

  class AbiturientServiceImpl(val notificationService: NotificationService) extends AbiturientService{
    override def registerAbiturient(abiturientDTO: AbiturientDTO): Abiturient = {
      val notification = Notification.Email("", "")
      val abiturient = Abiturient(UUID.randomUUID().toString, abiturientDTO.email, abiturientDTO.fio)
      //notificationService.sendNotification(notification)
      // save(abiturient)
      abiturient
    }
  }


}


 // recursion

object recursion {

  /**
   * Реализовать метод вычисления n!
   * n! = 1 * 2 * ... n
   */

  def fact(n: Int): Int = {
    var _n = 1
    var i = 2
    while (i <= n){
      _n *= i
      i += 1
    }
    _n
  }


  def factRec(n: Int): Int = {
    if( n <= 0) 1 else n * factRec(n - 1)
  }

  def factTailRec(n: Int): Int = {

    def loop(n: Int, accum: Int): Int =
      if( n <= 1) accum
      else loop(n - 1, n * accum)
    loop(n, 1)
  }



  /**
   * реализовать вычисление N числа Фибоначчи
   * F0 = 0, F1 = 1, Fn = Fn-1 + Fn - 2
   *
   */


}

object hof{

   trait Consumer{
       def subscribe(topic: String): LazyList[Record]
   }

   case class Record(value: String)

   case class Request()
   
   object Request {
       def parse(str: String): Request = ???
   }

  /**
   *
   * Реализовать ф-цию, которая будет читать записи Request из топика,
   * и сохранять их в базу
   */
   def createRequestSubscription() = {
     val cons: Consumer = ???
     val stream: LazyList[Record] = cons.subscribe("requests")
     stream.foreach{ rec =>
       val request = Request.parse(rec.value)
       // saveToDB(request)
     }
   }

  def createSubscription[T](topic: String)(action: LazyList[Record] => T): T = {
    val cons: Consumer = ???
    val stream: LazyList[Record] = cons.subscribe(topic)
    action(stream)
  }

  val createRequestSubscription2 = createSubscription("requests"){ l =>
    l.foreach{ r =>
      val request = Request.parse(r.value)
      // saveToDB(request)
    }
  }
  

  // обертки

  def logRunningTime[A, B](f: A => B): A => B = a => {
    val start = System.currentTimeMillis()
    val result = f(a)
    val end = System.currentTimeMillis()
    println(end - start)
    result
  }

  def doomy(str: String): Unit = {
    Thread.sleep(1000)
    println(str)
  }

  val lDoomy: String => Unit = logRunningTime(doomy)

  lDoomy("Hello")




  // изменение поведения ф-ции

  val arr = Array(1, 2, 3, 4, 5)

  def isOdd(i: Int): Boolean = i % 2 > 0

  def not[A](f: A => Boolean): A => Boolean = a => !f(a)
  
  lazy val isEven: Int => Boolean = not(isOdd)
  isOdd(2) // boolean
  isEven(3) // boolean




  // изменение самой функции

  // Follow type implementation

  def partial[A, B, C](a: A, f: (A, B) => C): B => C =
    b => f(a, b)

  def sum(x: Int, y: Int): Int = x + y

  val p: Int => Int = partial(3, sum)

  p(3) // 6
  p(2) // 5

}






/**
 *  Реализуем тип Option
 */


 object opt {

  /**
   *
   * Реализовать тип Option, который будет указывать на присутствие либо отсутсвие результата
   */

  // Covariant - animal родитель dog, Option[Animal] родитель Option[Dog]
  // Contravariant - animal родитель dog, Option[Dog] родитель Option[Animal]
  // Invariant - нет отношений

  // Вопрос вариантности

  trait Option[+T]{

    def isEmpty: Boolean = this match {
      case Option.None => true
      case Option.Some(v) => false
    }

    def get: T = this match {
      case Option.Some(v) => v
      case Option.None => throw new Exception("get on empty Option")
    }

    def map[B](f: T => B): Option[B] =
      flatMap(v => Option(f(v)))

    def flatMap[B](f: T => Option[B]): Option[B] = this match {
      case Option.None => Option.None
      case Option.Some(v) => f(v)
    }

    def printIfAny[T]():  Unit = this match {
      case Option.Some(v) => println(v)
      case Option.None =>
    }

    def zip[B](a: Option[B]): Option[(T,B)]  = this match {
      case Option.Some(v) => (a) match {
          case Option.None => Option.None
          case Option.Some(a) => Option.Some((v,a))
        }
      case Option.None => Option.None
    }

    def filter(f: T => Boolean):  Option[T] = this match {
      case Option.Some(v) => if(f(v)) Option.Some(v) else Option.None
      case Option.None => Option.None
    }

  }

  val a: Option[Int] = ???

  val r: Option[Int] = a.map(i => i + 1)


  object Option{

    final case class Some[T](v: T) extends Option[T]
    final case object None extends Option[Nothing]

    def apply[T](v: T): Option[T] = Some(v)
  }


  /**
   *
   * Реализовать метод printIfAny, который будет печатать значение, если оно есть
   */


  /**
   *
   * Реализовать метод zip, который будет создавать Option от пары значений из 2-х Option
   */

  /**
   *
   * Реализовать метод filter, который будет возвращать не пустой Option
   * в случае если исходный не пуст и предикат от значения = true
   */


 }

 object list {
   /**
    *
    * Реализовать односвязанный иммутабельный список List
    * Список имеет два случая:
    * Nil - пустой список
    * Cons - непустой, содердит первый элемент (голову) и хвост (оставшийся список)
    */

    trait List[+T]{

     def ::[TT >: T](elem: TT): List[TT] = this match {
       case List.::(_, _) => elem :: this
       case List.Nil => elem :: List.Nil
     }

     def mkString(sep: String): String = {
       def iter(t: List[T]): String = (t) match {
         case List.::(head, tail) => head + sep + iter(tail)
         case List.Nil => ""
       }
       iter(this)
     }

     def apply[T](v: T*): List[T] = if(v.isEmpty) List.Nil
      else new ::(v.head, apply(v.tail:_*))

     def reverse(): List[T] = {
       @tailrec
        def rev(t: List[T], rl: List[T]): List[T] = (t) match {
          case ::(head, tail) => rev(tail, List.::(head,rl))
          case List.Nil => rl
        }
       rev(this, List.Nil)
     }

     def map[B](f: T => B): List[B] = {
       def iter(t: List[T]): List[B] = (t) match {
         case ::(head, tail) => List.::(f(head),iter(tail))
         case List.Nil => List.Nil
       }
       iter(this)
     }

     def filter(f: T => Boolean):  List[T] = {
       def iter(t: List[T]): List[T] = (t) match {
         case ::(head, tail) => if(f(head)) List.::(head,iter(tail)) else iter(tail)
         case List.Nil => List.Nil
       }
       iter(this)
     }

   }

   object List{
     case class ::[T](head: T, tail: List[T]) extends List[T]
     case object Nil extends List[Nothing]


     def apply[T](v: T*): List[T] = if(v.isEmpty) List.Nil
      else new ::(v.head, apply(v.tail:_*))

   }

   case class A(var a: String)



   /**
     * Метод cons, добавляет элемент в голову списка, для этого метода можно воспользоваться названием `::`
     *
     */

    /**
      * Метод mkString возвращает строковое представление списка, с учетом переданного разделителя
      *
      */

    /**
      * Конструктор, позволяющий создать список из N - го числа аргументов
      * Для этого можно воспользоваться *
      * 
      * Например вот этот метод принимает некую последовательность аргументов с типом Int и выводит их на печать
      * def printArgs(args: Int*) = args.foreach(println(_))
      */

    /**
      *
      * Реализовать метод reverse который позволит заменить порядок элементов в списке на противоположный
      */

    /**
      *
      * Реализовать метод map для списка который будет применять некую ф-цию к элементам данного списка
      */


    /**
      *
      * Реализовать метод filter для списка который будет фильтровать список по некому условию
      */

    /**
      *
      * Написать функцию incList котрая будет принимать список Int и возвращать список,
      * где каждый элемент будет увеличен на 1
      */


    /**
      *
      * Написать функцию shoutString котрая будет принимать список String и возвращать список,
      * где к каждому элементу будет добавлен префикс в виде '!'
      */

 }