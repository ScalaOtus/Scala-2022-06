package module1.lesson5

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

class ForComp {
  /**
   * Необходимо будет "проверить" формулу условной вероятности
   * https://www.matburo.ru/tvbook_sub.php?p=par15
   * Если кратко:
   * В урне 3 белых и 3 черных шара. Из урны дважды вынимают по одному шару, не возвращая их обратно. Найти вероятность появления белого шара при втором испытании (событие В), если при первом испытании был извлечен черный шар (событие А).
   * Как будем делать:
   *
   * создать класс с моделированием эксперимента, в нем должна быть коллекция (List) моделирующая урну с шариками (1 - белый шарик, 0 - черный шарик) и функция случайного выбора 2х шариков без возвращения (scala.util.Random), возвращать эта функция должна true (если первый шарик был черный, а второй белый) и false (в противном случае)
   * создать коллекцию обьектов этих классов, скажем 10000 элементов, и провести этот эксперимент (функция map)
   * посчитать количество элементов массива из пункта 2 где функция вернула true, это количество поделенное на общее количество элементов массива и даст условную вероятность, которая должна быть около 3/5
   * PS: чем больше будет количество опытов в пункте 2, тем ближе будет результат моделирования к аналитическому решению
   *
   * Критерии оценки:
   * Результат должен быть близок к ожидаемому
   * Использовать как можно меньше явных циклов, отдавая предпочтение высокоуровневым функциям, как то map, foreach, fold, filter
   * Используйте только стандартные библиотеки из базового набора
   */
  def takeRandEl[Int](Lists1: ListBuffer[Int], acc: List[Int] = List[Int]()): List[Int] = {
    if (Lists1.isEmpty) acc
    else {
      val randomIndex = scala.util.Random.nextInt(Lists1.size)
      val x = Lists1(randomIndex)
      Lists1.remove(randomIndex)
      takeRandEl(Lists1, acc.::(x))
    }
  }

  @tailrec
  final def takeRandPar(Lists: ListBuffer[Int], cnt: Int = 10000, acc1: List[Double] = List[Double]()): List[Double] = {
    if (cnt == 0) acc1
    else {
      val z = takeRandEl(Lists.clone()).sliding(2, 2).toList
      val x = z.partition(x => x(0) < x(1))
      takeRandPar(Lists, cnt - 1, acc1.::(x._1.size.toDouble / z.size))
    }
  }
  def result(Lists: ListBuffer[Int], cnt: Int = 10000): Double = takeRandPar(Lists, cnt).sum / cnt

}
