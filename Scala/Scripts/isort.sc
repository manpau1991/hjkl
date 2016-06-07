/**
  * Implements the insertion sort algorithm on lists of arbitrary type `T`.
  *
  * @param xs the list to sort
  * @tparam T the type of the elements in the list
  * @return the sorted list
  */
def isort[T <% Ordered[T]](xs: List[T]): List[T] = {

  /**
    * Helper function that inserts the given element `T` into the given list with repect to the
    * defined ordering.
    *
    * @param x the element to insert
    * @param xs the list into which the element to insert
    * @return the list with `x` at the correct position with respect the defined ordering
    */
  def insert(x: T, xs: List[T]): List[T] = {
    xs match {
      case Nil => x :: xs
      case y :: ys => if (x <= y) x :: xs else y :: insert(x, ys)
    }
  }

  xs match {
    case Nil => Nil
    case y :: ys => insert(y, isort(ys))
  }
}

println(isort(List(-1,5, 1, 9, 4, 1, 5, 6, 3, 0)))
