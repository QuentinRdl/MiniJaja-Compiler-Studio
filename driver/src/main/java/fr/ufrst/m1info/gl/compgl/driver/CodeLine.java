package fr.ufrst.m1info.gl.compgl.driver;

/*
* The CodeLine class represents a single line of code in the program.
* It stores both the line number and the actual code written on that line.
* This class is mainly used to model each line that appears in the code editor or ListView.
* */
public class CodeLine {
    final private int lineNumber;
    private String code;

    public CodeLine(int lineNumber, String code){
        this.lineNumber = lineNumber;
        this.code = code;
    }

    public int getLineNumber(){
        return lineNumber;
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }
}
