! Multiplies the given matrix of dimension `rows` times `columns` by the given scalar.
!
! @param matrix the matrix to scale
! @param rows the number of rows in the matrix
! @param columns the number of columns in the matrix
! @param scalar the scalar value to multiply by
subroutine scalar_mult(matrix, rows, columns, scalar)
    implicit none
    real*8,  intent(inout) :: matrix(rows, columns)
    integer, intent(in)    :: rows
    integer, intent(in)    :: columns
    real*8,  intent(in)    :: scalar

    matrix = scalar * matrix
end subroutine
