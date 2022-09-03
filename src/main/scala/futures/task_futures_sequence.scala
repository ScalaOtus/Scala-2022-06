package futures

import HomeworksUtils.TaskSyntax

import java.util.concurrent.Executors
import scala.collection.mutable
import scala.collection.mutable.Builder
import scala.concurrent.{ExecutionContext, ExecutionContextExecutorService, Future, Promise}
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
    val p = Promise[(List[A], List[Throwable])]
    // Сворачиваем влево в Future списка
    futures.foldLeft(Future.successful(List[Any]())){
      (fr, fa) => fr.zipWith(fa.recover(e => e))((a, b) => a :+ b)
    }.onComplete{
      // Callback при окончании всех Future в списке
      case Failure(exception) => p.failure(exception) // Если провалилась сборка листа - возвращаем Failed
      case Success(value) => p.success {
        // Фильтруем результат - он не Throwable
        // (Не используем isInstanceOf[A] т.к. Nothing (aka Throwable) подтип всего)
        val successful = value.filter(e => !e.isInstanceOf[Throwable]).map(e => e.asInstanceOf[A])
        val failed = value.filter(e => e.isInstanceOf[Throwable]).map(e => e.asInstanceOf[Throwable])
        (successful, failed)
      }
    }
    p.future
  }
}
