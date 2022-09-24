package futures

import HomeworksUtils.TaskSyntax

import java.util.concurrent.Executors
import scala.collection.mutable
import scala.collection.mutable.Builder
import scala.concurrent.duration.{Duration, SECONDS}
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, ExecutionContextExecutorService, Future, Promise}
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
    val mapFuture: Future[A] => Future[(List[A], List[Throwable])] =
      v => v.map(v => (List[A](v), List[Throwable]()))
        .recover(e => (List[A](), List[Throwable](e)));

    futures.foldLeft(Future(List[A](), List[Throwable]())){(acc, v) =>
      acc.zipWith(mapFuture(v))((a, b) => (a._1 ::: b._1, a._2 ::: b._2))
    }
  }

  def main(args: Array[String]): Unit = {
    implicit val ex: ExecutionContextExecutor = ExecutionContext.parasitic;
    fullSequence(
      Future(35)
        :: Future(14)
        :: Future(throw new IllegalArgumentException("Exception 1"))
        :: Future(throw new IllegalArgumentException("Exception 2"))
        :: Future(throw new IllegalArgumentException("Exception 3"))
        :: Future(48)
        :: Nil).onComplete(println(_))
  }
}
