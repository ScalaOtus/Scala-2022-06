
val demoColl = List(1,2,3,4,5)::List(1,0,60)::List(7,4) :: Nil

demoColl.foreach(x=> if(x.sum > 12) println(x.mkString(";")))


