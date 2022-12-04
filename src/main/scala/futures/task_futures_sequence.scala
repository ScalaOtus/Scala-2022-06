package futures

import HomeworksUtils.TaskSyntax

import scala.annotation.{tailrec, unused}
import scala.concurrent
import scala.concurrent.impl.Promise
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.{Failure, Success, Try}

object task_futures_sequence {

  /**
   * В данном задании Вам предлагается реализовать функцию fullSequence,
   * похожую на Future.sequence, но в отличии от нее,
   * возвращающую все успешные и не успешные результаты.
   * Возвращаемое тип функции - кортеж из двух списков,
   * в левом хранятся результаты успешных выполнений,
   * в правово результаты неуспешных выполнений.
   * Не допускается использование методов объекта Await и мутабельных переменных var
   */

  /**
   * @param futures список асинхронных задач
   * @return асинхронную задачу с кортежом из двух списков
   */
  def fullSequence[A](futures: List[Future[A]])
                     (implicit ex: ExecutionContext): Future[(List[A], List[Throwable])] = {
    futures.foldLeft(Future.successful((List.empty[A], List.empty[Throwable]))) { (f1, f2) =>
      f1.flatMap {
        case (value, value1) =>
        f2.map { s => (value :+ s, value1) }
          .recover { case e: Throwable => (value, value1 :+ e) }
      }
    }
  }


}
