package se.soprasteria.s2qaportal.model;
import java.io.Serializable;

public class TestCase implements Serializable {

    private int id;
    private String name;
    private String classPath;


    public TestCase(String name, String classPath){
        this.classPath = classPath;
        this.name = name;
    }

    public TestCase(int id, String name, String classPath){
        this.id = id;
        this.classPath = classPath;
        this.name = name;
    }

    public String getSQLValueString(){
        return "('"+getName()+"', '"+getClassPath()+"')";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }
}
