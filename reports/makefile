docs=main

all : documents clean

documents : dvi
	for i in $(docs); do pdflatex $$i.tex $$i.lof $$i.toc $$i.aux > /dev/null; done

dvi : *.tex
	for i in $(docs); do pdflatex $$i.tex; done

clean :
	rm -f *.aux *.lof *.log *.toc # Prevents the command from causing an error, and stopping the process

mrproper : clean
	rm *.pdf

start : all
	for i in $(docs); do evince $$i.pdf; done
