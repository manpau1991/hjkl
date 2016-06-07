/** Implements the merge sort algorithm.
  *
  * @param xs the list to sort
  * @tparam T the type of the elements to sort
  * @return the sorted list
  */
def mergesort[T <% Ordered[T]](xs: List[T]): List[T] = {

  /** Helper function that merges the two given sub-lists with regard to the defined ordering of
    * `T`.
    *
    * @param as the first sub-list
    * @param bs the second sub-list
    * @tparam T the type of the elements in the lists
    * @return the list resulting from the merge of `as` and `bs`
    */
  def merge(as: List[T], bs: List[T]): List[T] = (as, bs) match {
    case (_, Nil) => as
    case (Nil, _) => bs
    case (a :: as1, b :: bs1) =>
      if (a < b) a :: merge(as1, bs)
      else       b :: merge(as, bs1)
  }

  if (xs.isEmpty || xs.length == 1) xs
  else {
    val n = xs.length / 2
    val (l, r) = xs.splitAt(n)
    merge(mergesort(l), mergesort(r))
  }
}

println(mergesort(List(5, 9, 0, -1, 4, 2, 3)))
println(mergesort(List("banana", "apple", "pineapple", "orange")))
