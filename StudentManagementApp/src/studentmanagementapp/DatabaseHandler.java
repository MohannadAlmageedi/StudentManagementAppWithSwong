/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
public class DatabaseHandler {

    private static final String DB_URL = "jdbc:sqlite:students.db";

    public DatabaseHandler() {
        createTables();
    }

    // دالة لإنشاء الجداول (طلاب، معلمين، مواد)
    private void createTables() {
        // إنشاء جدول الطلاب
        String createStudentsTable = "CREATE TABLE IF NOT EXISTS students (" +
                                     "studentId TEXT PRIMARY KEY," +
                                     "name TEXT NOT NULL," +
                                     "birthDate DATE," +
                                     "birthPlace TEXT," +
                                     "gender TEXT," +
                                     "enrollmentStatus TEXT," +
                                     "photoPath TEXT" +
                                     ");";

        // إنشاء جدول المعلمين
        String createTeachersTable = "CREATE TABLE IF NOT EXISTS teachers (" +
                                     "teacherId TEXT PRIMARY KEY," +
                                     "name TEXT NOT NULL" +
                                     ");";

        // إنشاء جدول المواد
        String createSubjectsTable = "CREATE TABLE IF NOT EXISTS subjects (" +
                                     "subjectId TEXT PRIMARY KEY," +
                                     "name TEXT NOT NULL," +
                                     "teacherId TEXT," +
                                     "FOREIGN KEY (teacherId) REFERENCES teachers (teacherId)" +
                                     ");";
        
        
        
        
        
        // إنشاء جدول الدرجات
String createGradesTable = "CREATE TABLE IF NOT EXISTS grades (" +
                           "gradeId INTEGER PRIMARY KEY AUTOINCREMENT," +
                           "studentId TEXT NOT NULL," +
                           "subjectId TEXT NOT NULL," +
                           "score REAL NOT NULL," +
                           "FOREIGN KEY (studentId) REFERENCES students (studentId)," +
                           "FOREIGN KEY (subjectId) REFERENCES subjects (subjectId)" +
                           ");";

try (Connection conn = connect();
     PreparedStatement stmtGrades = conn.prepareStatement(createGradesTable)) {
    stmtGrades.execute();
} catch (SQLException e) {
    JOptionPane.showMessageDialog(null, e.getMessage());
}





        try (Connection conn = connect();
             PreparedStatement stmtStudents = conn.prepareStatement(createStudentsTable);
             PreparedStatement stmtTeachers = conn.prepareStatement(createTeachersTable);
             PreparedStatement stmtSubjects = conn.prepareStatement(createSubjectsTable)) {

            stmtStudents.execute();
            stmtTeachers.execute();
            stmtSubjects.execute();

        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }

    // دالة للاتصال بقاعدة البيانات
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return conn;
    }

    // دالة لإضافة طالب إلى قاعدة البيانات
    public void addStudent(Student student) {
        String sql = "INSERT INTO students(studentId, name, birthDate, birthPlace, gender, enrollmentStatus, photoPath) " +
                     "VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getStudentId());
            pstmt.setString(2, student.getName());
            pstmt.setDate(3, new Date(student.getBirthDate().getTime()));
            pstmt.setString(4, student.getBirthPlace());
            pstmt.setString(5, student.getGender());
            pstmt.setString(6, student.getEnrollmentStatus());
            pstmt.setString(7, student.getPhotoPath());
            pstmt.executeUpdate();
             JOptionPane.showMessageDialog(null, "Student added to dataBase");
        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }

    // دالة لإضافة معلم إلى قاعدة البيانات
    public void addTeacher(Teacher teacher) {
        String insertTeacherSql = "INSERT INTO teachers(teacherId, name) VALUES(?, ?)";
        String insertSubjectSql = "INSERT INTO subjects(subjectId, name, teacherId) VALUES(?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmtTeacher = conn.prepareStatement(insertTeacherSql);
             PreparedStatement pstmtSubject = conn.prepareStatement(insertSubjectSql)) {

            // إدخال المعلم إلى جدول المعلمين
            pstmtTeacher.setString(1, teacher.getTeacherId());
            pstmtTeacher.setString(2, teacher.getName());
            pstmtTeacher.executeUpdate();

            // إدخال المواد الخاصة بالمعلم في جدول المواد
            ArrayList<Subject> subjects = teacher.getSubjects();
            for (Subject subject : subjects) {
                pstmtSubject.setString(1, subject.getSubjectId());
                pstmtSubject.setString(2, subject.getName());
                pstmtSubject.setString(3, teacher.getTeacherId());  // ربط المعلم بالمادة باستخدام teacherId
                pstmtSubject.executeUpdate();
            }

         JOptionPane.showMessageDialog(null, "Teacher added Succefully");

        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }

    // دالة لإضافة مادة إلى قاعدة البيانات
    public void addSubject(Subject subject) {
        String insertSubjectSql = "INSERT INTO subjects(subjectId, name, teacherId) VALUES(?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmtSubject = conn.prepareStatement(insertSubjectSql)) {

            pstmtSubject.setString(1, subject.getSubjectId());
            pstmtSubject.setString(2, subject.getName());
            pstmtSubject.setString(3, subject.getTeacher().getTeacherId());  // ربط المادة بالمعلم
            pstmtSubject.executeUpdate();

          JOptionPane.showMessageDialog(null, "Teacher added Succefully ");

        } catch (SQLException e) {
          JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }//end of addSunject Method
    
    

  
    // دالة لاسترجاع جميع الطلاب من قاعدة البيانات
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT studentId, name, birthDate, birthPlace, gender, enrollmentStatus, photoPath FROM students";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String studentId = rs.getString("studentId");
                String name = rs.getString("name");
                Date birthDate = rs.getDate("birthDate"); // جلب تاريخ الميلاد
                String birthPlace = rs.getString("birthPlace");
                String gender = rs.getString("gender");
                String enrollmentStatus = rs.getString("enrollmentStatus");
                String photoPath = rs.getString("photoPath"); // جلب مسار الصورة

                // إنشاء كائن Student وإضافة البيانات المسترجعة
                students.add(new Student(studentId, name, birthDate, birthPlace, gender, enrollmentStatus, photoPath));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }

        return students;
    }//end get method
    
    
     public void deleteStudent(String studentId) {
        String sql = "DELETE FROM students WHERE studentId = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                 JOptionPane.showMessageDialog(null, "Studnet Deleted Sucsessfully");
            } else {
                JOptionPane.showMessageDialog(null, "Student not found!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
           
        }
    }// end delete method
     
      public void deleteTeacherById(String teacherId) {
        String deleteSubjectsSql = "DELETE FROM subjects WHERE teacherId = ?";
        String deleteTeacherSql = "DELETE FROM teachers WHERE teacherId = ?";

        try (Connection conn = connect();
             PreparedStatement pstmtSubjects = conn.prepareStatement(deleteSubjectsSql);
             PreparedStatement pstmtTeacher = conn.prepareStatement(deleteTeacherSql)) {

            // حذف المواد المرتبطة بالمعلم أولاً
            pstmtSubjects.setString(1, teacherId);
            pstmtSubjects.executeUpdate();

            // حذف المعلم من جدول المعلمين
            pstmtTeacher.setString(1, teacherId);
            int affectedRows = pstmtTeacher.executeUpdate();

            // التحقق من حذف المعلم بنجاح
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(null, "Teacher deleted successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Teacher not found!");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//end delete teacher method
      
      
      
      
      
// دالة لاسترجاع جميع المعلمين مع المواد المرتبطة بهم
public ArrayList<Teacher> getAllTeachersWithSubjects() {
    ArrayList<Teacher> teachers = new ArrayList<>();
    String sqlTeachers = "SELECT teacherId, name FROM teachers";
    String sqlSubjects = "SELECT subjectId, name FROM subjects WHERE teacherId = ?";

    try (Connection conn = connect();
         PreparedStatement pstmtTeachers = conn.prepareStatement(sqlTeachers);
         ResultSet rsTeachers = pstmtTeachers.executeQuery()) {

        while (rsTeachers.next()) {
            String teacherId = rsTeachers.getString("teacherId");
            String name = rsTeachers.getString("name");

            // إنشاء كائن Teacher
            Teacher teacher = new Teacher(teacherId, name);

            // جلب المواد الخاصة بالمعلم وإضافتها لكائن Teacher
            try (PreparedStatement pstmtSubjects = conn.prepareStatement(sqlSubjects)) {
                pstmtSubjects.setString(1, teacherId);
                try (ResultSet rsSubjects = pstmtSubjects.executeQuery()) {
                    while (rsSubjects.next()) {
                        String subjectId = rsSubjects.getString("subjectId");
                        String subjectName = rsSubjects.getString("name");

                        // إنشاء كائن Subject وربط المعلم به
                        Subject subject = new Subject(subjectId, subjectName, teacher);
                        teacher.addSubject(subject);
                    }
                }
            }

            // إضافة المعلم إلى قائمة المعلمين
            teachers.add(teacher);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }

    return teachers;
}//end of getAllTeacher




// دالة لاسترجاع جميع المواد من قاعدة البيانات
public ArrayList<Subject> getAllSubjects() {
    ArrayList<Subject> subjects = new ArrayList<>();
    String sqlSubjects = "SELECT subjectId, name, teacherId FROM subjects";
    String sqlTeacher = "SELECT teacherId, name FROM teachers WHERE teacherId = ?";

    try (Connection conn = connect();
         PreparedStatement pstmtSubjects = conn.prepareStatement(sqlSubjects);
         ResultSet rsSubjects = pstmtSubjects.executeQuery()) {

        while (rsSubjects.next()) {
            String subjectId = rsSubjects.getString("subjectId");
            String subjectName = rsSubjects.getString("name");
            String teacherId = rsSubjects.getString("teacherId");

            Teacher teacher = null;

            // جلب بيانات المعلم المرتبطة بالمادة
            try (PreparedStatement pstmtTeacher = conn.prepareStatement(sqlTeacher)) {
                pstmtTeacher.setString(1, teacherId);
                try (ResultSet rsTeacher = pstmtTeacher.executeQuery()) {
                    if (rsTeacher.next()) {
                        String teacherName = rsTeacher.getString("name");
                        teacher = new Teacher(teacherId, teacherName);
                    }
                }
            }

            // إنشاء كائن Subject وإضافته إلى القائمة
            Subject subject = new Subject(subjectId, subjectName, teacher);
            subjects.add(subject);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }

    return subjects;
}

public ArrayList<Subject> getSubjectsForStudent(String studentId) {
    ArrayList<Subject> subjects = new ArrayList<>();
    String sql = "SELECT DISTINCT s.subjectId, s.name AS subjectName, t.teacherId, t.name AS teacherName " +
                 "FROM subjects s " +
                 "LEFT JOIN teachers t ON s.teacherId = t.teacherId " +
                 "LEFT JOIN grades g ON s.subjectId = g.subjectId AND g.studentId = ?";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, studentId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String subjectId = rs.getString("subjectId");
                String subjectName = rs.getString("subjectName");
                String teacherId = rs.getString("teacherId");
                String teacherName = rs.getString("teacherName");

                // إنشاء كائن Teacher
                Teacher teacher = new Teacher(teacherId, teacherName);

                // إنشاء كائن Subject وربط المعلم به
                Subject subject = new Subject(subjectId, subjectName, teacher);
                subjects.add(subject);
            }
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }

    return subjects;
}


// دالة لإضافة درجة
public void addGrade(String studentId, String subjectId, double score) {
    String sql = "INSERT INTO grades(studentId, subjectId, score) VALUES(?, ?, ?)";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, studentId);
        pstmt.setString(2, subjectId);
        pstmt.setDouble(3, score);
        pstmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Grade added successfully");

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}



// دالة لاسترجاع الدرجات
public ArrayList<Grade> getGrades(String studentId) {
    ArrayList<Grade> grades = new ArrayList<>();
    String sql = "SELECT g.score, s.subjectId, s.name, t.teacherId, t.name AS teacherName " +
                 "FROM grades g " +
                 "JOIN subjects s ON g.subjectId = s.subjectId " +
                 "JOIN teachers t ON s.teacherId = t.teacherId " +
                 "WHERE g.studentId = ?";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, studentId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String subjectId = rs.getString("subjectId");
                String subjectName = rs.getString("name");
                String teacherId = rs.getString("teacherId");
                String teacherName = rs.getString("teacherName");
                double score = rs.getDouble("score");

                // إنشاء كائن Teacher
                Teacher teacher = new Teacher(teacherId, teacherName);

                // إنشاء كائن Subject
                Subject subject = new Subject(subjectId, subjectName, teacher);

                // إنشاء كائن Grade
                Grade grade = new Grade(subject, score);
                grades.add(grade);
            }
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }

    return grades;
}



public ArrayList<Grade> getGradesByStudentId(String studentId) {
    ArrayList<Grade> grades = new ArrayList<>();
    String sql = "SELECT g.score, s.subjectId, s.name AS subjectName " +
                 "FROM grades g " +
                 "JOIN subjects s ON g.subjectId = s.subjectId " +
                 "WHERE g.studentId = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, studentId); // إدخال رقم الطالب
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                double score = rs.getDouble("score");
                String subjectId = rs.getString("subjectId");
                String subjectName = rs.getString("subjectName");

                // إنشاء كائن Subject
                Subject subject = new Subject(subjectId, subjectName, null);

                // إنشاء كائن Grade وربطه بالمادة
                grades.add(new Grade(subject, score));
            }
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }

    return grades;
}



      
      
}
