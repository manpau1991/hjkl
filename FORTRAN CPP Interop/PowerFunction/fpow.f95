! This is the FORTRAN function we want to call in our C code.
! It computes `xbase` to the power of `xexponent` and returns the result `y`.
function fpow(xbase, xexponent) result(y)
    real*8, intent(in) :: xbase
    real*8, intent(in) :: xexponent
    real*8             :: y

    y = xbase ** xexponent
end function fpow