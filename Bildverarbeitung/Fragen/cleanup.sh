#!/bin/bash

for file in $(find . -type f -name "*.out" -o -name "*.aux" -o -name "*.log")
do
    printf "Removing %s ..." $file;
    rm $file;
    printf "done.\n";
done

