#!/bin/bash

function compile () {
    echo "compiling $1 .........";
    cd $1;
    pdflatex solution.tex;
    rm -f *.aux;
    rm -f *.fdb_latexmk;
    rm -f *.fls;
    rm -f *.log;
    rm -f *.out
    cd ..;
    echo "compiling $1 finished!";
    echo "";
}

if [ "$1" = all ]; then
    for d in `ls -d hw*/`; do
        compile $d &
    done
else
    compile hw$1
fi

