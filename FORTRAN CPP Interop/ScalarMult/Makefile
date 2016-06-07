OBJS 	= fmatrix2d.o main.o matfunctions.o
CC 	= g++
CFLAGS	= -c
FC 	= gfortran
LFLAGS	= -o $(EXEC) -lgfortran
EXEC	= fmat

all: $(EXEC)

$(EXEC): $(OBJS)
	$(CC) $(LFLAGS) $^

matfunctions.o: matfunctions.f95
	$(FC) $(CFLAGS) matfunctions.f95

fmatrix2d.o: fmatrix2d.cpp fmatrix2d.h
	$(CC) $(CFLAGS) fmatrix2d.cpp

main.o: main.cpp fmatrix2d.h
	$(CC) $(CFLAGS) main.cpp

clean:
	@echo "Removing executable file" $(EXEC) "and all object files..."
	rm -f *.o *~ $(EXEC)
	@echo "Done."

.PHONY: all clean
