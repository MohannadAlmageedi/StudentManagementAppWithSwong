/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementapp;

import java.util.ArrayList;
import java.util.Date;

public class Student {
    private String studentId;
    private String name;
    private Date birthDate;
    private String birthPlace;
    private String gender;
    private String enrollmentStatus;
    private ArrayList<Grade> grades;
    private ArrayList<Subject> subjects;
    private String photoPath;

    public Student(String studentId, String name, Date birthDate, String birthPlace, String gender, String enrollmentStatus, String photoPath) {
        this.studentId = studentId;
        this.name = name;
        this.birthDate = birthDate;
        this.birthPlace = birthPlace;
        this.gender = gender;
        this.enrollmentStatus = enrollmentStatus;
        this.grades = new ArrayList<>();
        this.subjects=new ArrayList<>();
        this.photoPath = photoPath;
    }

    
    
    
    
    public void addGrade(Grade grade) {
        grades.add(grade);
    }
    
    public void addSubject(Subject subject)
    {
        subjects.add(subject);
    }
    

    public double calculateAverage() {
        double total = 0;
        for (Grade grade : grades) {
            total += grade.getScore();
        }
        return grades.size() > 0 ? total / grades.size() : 0;
    }

    // Getters and setters for all properties

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }
    
    public ArrayList<Subject> getSubject()
    {
        return subjects;
    }

    public void setGrades(ArrayList<Grade> grades) {
        this.grades = grades;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
