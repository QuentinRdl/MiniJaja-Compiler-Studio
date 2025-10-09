package fr.ufrst.m1info.gl.compgl.driver;

/*
* The CodeLine class represents a single line of code in the program.
* It stores both the line number and the actual code written on that line.
* This class is mainly used to model each line that appears in the code editor or ListView.
* */
public class CodeLine {
    final private int lineNumber;
    private String code;

    /**
     * Creates a new CodeLine with a specific line number and code content.
     *
     * @param lineNumber the number of the line in the source code
     * @param code       the text content of the line
     */
    public CodeLine(int lineNumber, String code){
        this.lineNumber = lineNumber;
        this.code = code;
    }

    /**
     * Returns the line number of this code line.
     *
     * @return the line number
     */
    public int getLineNumber(){
        return lineNumber;
    }

    /**
     * Returns the text of this code line.
     *
     * @return the code content
     */
    public String getCode(){
        return code;
    }

    /**
     * Updates the text content of this code line.
     *
     * @param code the new code to set
     */
    public void setCode(String code){
        this.code = code;
    }
}
