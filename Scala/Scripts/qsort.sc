/**
  * Implements the quicksort algorithm.
  *
  * @param xs the list to sort
  * @tparam T the type of the elements to sort
  * @return the sorted list
  */
def quicksort[T <% Ordered[T]](xs: List[T]): List[T] = xs match {
  case Nil | List(_) => xs
  case y :: ys =>
    val (lty, gty) = ys.partition(_ < y)
    quicksort(lty) ::: y :: quicksort(gty)
}

println(quicksort(Nil))
println(quicksort(List(0)))
println(quicksort(List(1, 2)))
println(quicksort(List(5, 9, 0, -1, 4, 2, 3)))
