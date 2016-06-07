! This is just an ordinary FORTRAN function we want to call from C.
! It does nothing special but to print something to the console.
function HELLO()
    print *, "Hello, World from FORTRAN!"
end function HELLO
