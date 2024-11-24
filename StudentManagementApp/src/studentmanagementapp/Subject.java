/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementapp;

public class Subject {
    private String subjectId;
    private String name;
    private Teacher teacher;

    public Subject(String subjectId, String name, Teacher teacher) {
        this.subjectId = subjectId;
        this.name = name;
        this.teacher = teacher;
    }
    
    
    
    

    // Getters and setters
    @Override
public String toString() {
    return this.name; 
}


    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
