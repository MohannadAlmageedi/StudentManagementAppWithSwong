/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package studentmanagementapp;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;


public class StudentManagementApp extends JFrame {

    private ArrayList<Student> students;
    private ArrayList<Teacher> teachers;
    private ArrayList<Subject> subjects;
    private DatabaseHandler dbHandler;
    private FileViewer file;
    private static final String U_F1="C:\\Users\\M C\\Desktop\\database\\befor.txt";
    private static final String U_F2="C:\\Users\\M C\\Desktop\\database\\after.txt";
    
    private static final String DB_URL = "jdbc:sqlite:students.db";
    

    public StudentManagementApp() {
        students = new ArrayList<>();
        teachers = new ArrayList<>();
        subjects = new ArrayList<>();
         dbHandler = new DatabaseHandler();
        file=new FileViewer();
         

        setTitle("Student Management System");
        ImageIcon icon = new ImageIcon("images\\main_background1.jpeg");
        setIconImage(icon.getImage());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        JPanel menuPanelEast = new JPanel(new GridLayout(0, 1, 10, 10));
        add(menuPanel, BorderLayout.WEST);
        add(menuPanelEast, BorderLayout.EAST);

        JButton addStudentBtn = new JButton("Add Student");
        JButton addTeacherBtn = new JButton("Add Teacher");
        JButton addSubjectBtn = new JButton("Add Subject");
        JButton enterGradesBtn = new JButton("Enter Grades");
        JButton searchStudentBtn = new JButton("Search Student");
        JButton showAllStudentsBtn = new JButton("Show All Students");
        JButton showStudentsWithAvgBtn = new JButton("Show Students with Average");

        JButton displayStudentGradesBtn = new JButton("Display Student Grades");
        JButton displayTopThreeStudentsBtn = new JButton("Display Top Student");

        JButton deleteStudentBtn = new JButton("Delete Student");
        JButton deleteTeacherBtn = new JButton("Delete Teacher");
        JButton showStatisticsBtn = new JButton(" showStatistics");

        JMenuBar menuBar = new JMenuBar();

        JMenu helpMenu = new JMenu("Help");
        JMenuItem howToUseItem = new JMenuItem("How to Use");
        JMenuItem benefitsItem = new JMenuItem("Benefits of This App");

        helpMenu.add(howToUseItem);
        helpMenu.add(benefitsItem);
        
        JMenu servicesMenue=new JMenu("Services");  //add services menue 
        JMenuItem addStudnet=new JMenuItem("Add Student");
        JMenuItem AddTeacher=new JMenuItem("Add Teacher");
        JMenuItem AddSubject=new JMenuItem("Add Subject");
        JMenuItem AddGrade=new JMenuItem("Add Grade");
        JMenuItem SearchStudent=new JMenuItem("Search Student");
        JMenuItem ShowAllStudent=new JMenuItem("Show All Student");
        JMenuItem ShowStudentswithAverage=new JMenuItem("Show Students with Average");
        JMenuItem DisplayTopStudent=new JMenuItem("Display Top Student");
        JMenuItem DisplayStudentGrade=new JMenuItem("Display Student Grade");
        JMenuItem DeleteStudent=new JMenuItem("Delete Student");
        JMenuItem DeleteTeacher=new JMenuItem("Delete Teacher");
        JMenuItem ShowStatistics=new JMenuItem("Show Statistics");
        servicesMenue.add(addStudnet);
         servicesMenue.add(AddTeacher);
         servicesMenue.add(AddSubject);
         servicesMenue.add(AddSubject);
         servicesMenue.add(AddGrade);
         servicesMenue.add(SearchStudent);
         servicesMenue.add(ShowAllStudent);
         servicesMenue.add(ShowStudentswithAverage);
         servicesMenue.add(DisplayTopStudent);
         servicesMenue.add(DisplayStudentGrade);
         servicesMenue.add(DeleteStudent);
         servicesMenue.add(DeleteTeacher);
         servicesMenue.add(ShowStatistics);
         
         
         
                 

        menuBar.add(helpMenu);
        menuBar.add(servicesMenue);

        setJMenuBar(menuBar);

        menuPanel.add(addStudentBtn);
        menuPanel.add(addTeacherBtn);
        menuPanel.add(addSubjectBtn);
        menuPanel.add(enterGradesBtn);
        menuPanel.add(searchStudentBtn);
        menuPanel.add(showAllStudentsBtn);
        menuPanel.add(showStudentsWithAvgBtn);

        menuPanelEast.add(displayTopThreeStudentsBtn);
        menuPanelEast.add(displayStudentGradesBtn);
        menuPanelEast.add(deleteStudentBtn);
        menuPanelEast.add(deleteTeacherBtn);
        menuPanelEast.add(showStatisticsBtn);

        menuPanelEast.add(showStatisticsBtn);

        //to format button
        styleButton(addStudentBtn);
        styleButton(addTeacherBtn);
        styleButton(addSubjectBtn);
        styleButton(enterGradesBtn);
        styleButton(searchStudentBtn);
        styleButton(showAllStudentsBtn);
        styleButton(showStudentsWithAvgBtn);

        //Main Panel to create an image background
        JPanel mainPanel = new JPanel();
        add(mainPanel, BorderLayout.CENTER);

        JLabel backgroundLabel = new JLabel(new ImageIcon("images\\main_background2.jpeg"));
        mainPanel.add(backgroundLabel);
        backgroundLabel.setLayout(new FlowLayout());

        addStudentBtn.addActionListener(e -> showAddStudentDialog());
        addTeacherBtn.addActionListener(e -> showAddTeacherDialog());
        addSubjectBtn.addActionListener(e -> showAddSubjectDialog());
        enterGradesBtn.addActionListener(e -> enterGrades());
        searchStudentBtn.addActionListener(e -> searchStudent());
        showAllStudentsBtn.addActionListener(e -> showAllStudents());
        showStudentsWithAvgBtn.addActionListener(e -> showStudentsWithAvg());

        displayTopThreeStudentsBtn.addActionListener(e -> displayTopThreeStudents());
        displayStudentGradesBtn.addActionListener(e -> displayStudentGrades());
        deleteStudentBtn.addActionListener(e -> deleteStudent());
        deleteTeacherBtn.addActionListener(e -> deleteTeacher());
        showStatisticsBtn.addActionListener(e -> showStatistics());
        
        howToUseItem.addActionListener(e -> showHowToUse());
    benefitsItem.addActionListener(e -> showBenefits());
    
    
    addStudnet.addActionListener(e->showAddStudentDialog());
     AddTeacher.addActionListener(e->showAddTeacherDialog());
      AddSubject.addActionListener(e->showAddSubjectDialog());
       AddGrade.addActionListener(e->enterGrades());
        SearchStudent.addActionListener(e->searchStudent());
         ShowAllStudent.addActionListener(e->showAllStudents());
          ShowStudentswithAverage.addActionListener(e->showStudentsWithAvg());
           DisplayTopStudent.addActionListener(e->displayTopThreeStudents());
            DisplayStudentGrade.addActionListener(e->displayStudentGrades());
             DeleteStudent.addActionListener(e->deleteStudent());
              DeleteTeacher.addActionListener(e->deleteTeacher());
               ShowStatistics.addActionListener(e->showStatistics());
        
       
    
    

    }

    private void styleButton(JButton button) {
     
       
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);           
        button.setBorderPainted(false);          
        button.setOpaque(true);                  
    }

   
    private void showAddStudentDialog() {
        JDialog dialog = new JDialog(this, "Add Student", true);
        dialog.setSize(400, 500); 
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        dialog.add(panel);

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel idLabel = new JLabel("Student ID:");
        JTextField idField = new JTextField(15);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(15);

        JLabel birthPlaceLabel = new JLabel("Birth Place:");
        JTextField birthPlaceField = new JTextField(15);

        JLabel genderLabel = new JLabel("Gender:");
        JRadioButton maleRadio = new JRadioButton("Male");
        JRadioButton femaleRadio = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);

        JLabel enrollmentStatusLabel = new JLabel("Enrollment Status:");
        JComboBox<String> enrollmentStatusCombo = new JComboBox<>(new String[]{"Enrolled", "Not Enrolled"});

        JLabel photoLabel = new JLabel("Photo Path:");
        JTextField photoField = new JTextField(15);

       
        JLabel subjectsLabel = new JLabel("Select Subjects:");
        DefaultListModel<Subject> subjectListModel = new DefaultListModel<>();

      
        for (Subject subject : dbHandler.getAllSubjects()) {
            subjectListModel.addElement(subject); 
        }  

        JList<Subject> subjectList = new JList<>(subjectListModel);
        subjectList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane subjectScrollPane = new JScrollPane(subjectList);
        subjectScrollPane.setPreferredSize(new Dimension(150, 100)); // ضبط حجم القائمة

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String studentId = idField.getText();
            String name = nameField.getText();
            String birthPlace = birthPlaceField.getText();
            String gender = maleRadio.isSelected() ? "Male" : "Female";
            String enrollmentStatus = (String) enrollmentStatusCombo.getSelectedItem();
            String photoPath = photoField.getText();

            // تحقق من وجود الطالب بنفس ID
            boolean idExists = false;
            for (Student student : dbHandler.getAllStudents()) {
                if (student.getStudentId().equals(studentId)) {
                    idExists = true;
                    break;
                }
            }

            if (idExists) {
                JOptionPane.showMessageDialog(dialog, "A student with this ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // إنشاء طالب جديد
                Student student = new Student(studentId, name, new Date(), birthPlace, gender, enrollmentStatus, photoPath);

                // إضافة المواد المختارة للطالب
                for (Subject selectedSubject : subjectList.getSelectedValuesList()) {
                    student.addSubject(selectedSubject);
                }

                students.add(student);//ad student to ArrayList
                 dbHandler.addStudent(student);//add student to data base
               // JOptionPane.showMessageDialog(dialog, "Student added successfully!");
                dialog.dispose();
            }
        });

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(idLabel)
                        .addComponent(nameLabel)
                        .addComponent(birthPlaceLabel)
                        .addComponent(genderLabel)
                        .addComponent(enrollmentStatusLabel)
                        .addComponent(photoLabel)
                        .addComponent(subjectsLabel)
                        .addComponent(submitButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(idField)
                        .addComponent(nameField)
                        .addComponent(birthPlaceField)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(maleRadio)
                                .addComponent(femaleRadio))
                        .addComponent(enrollmentStatusCombo)
                        .addComponent(photoField)
                        .addComponent(subjectScrollPane)) // إضافة حقل اختيار المواد
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel)
                        .addComponent(idField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(nameLabel)
                        .addComponent(nameField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(birthPlaceLabel)
                        .addComponent(birthPlaceField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(genderLabel)
                        .addComponent(maleRadio)
                        .addComponent(femaleRadio))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(enrollmentStatusLabel)
                        .addComponent(enrollmentStatusCombo))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(photoLabel)
                        .addComponent(photoField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(subjectsLabel)
                        .addComponent(subjectScrollPane)) // إضافة حقل اختيار المواد
                .addComponent(submitButton)
        );

        dialog.setVisible(true);
    }

    // Dialog to add a new teacher
    private void showAddTeacherDialog() {
        JDialog dialog = new JDialog(this, "Add Teacher", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        dialog.add(panel);

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel idLabel = new JLabel("Teacher ID:");
        JTextField idField = new JTextField(15);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(15);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String teacherId = idField.getText();
            String name = nameField.getText();

            // تحقق من وجود أستاذ بنفس ID
            boolean idExists = false;
            for (Teacher teacher : teachers) {
                if (teacher.getTeacherId().equals(teacherId)) {
                    idExists = true;
                    break;
                }
            }

            if (idExists) {
                JOptionPane.showMessageDialog(dialog, "A teacher with this ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // إنشاء أستاذ جديد
                Teacher teacher = new Teacher(teacherId, name);
                teachers.add(teacher);
                dbHandler.addTeacher(teacher);
               // JOptionPane.showMessageDialog(dialog, "Teacher added successfully!");
                dialog.dispose();
            }
        });

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(idLabel)
                        .addComponent(nameLabel)
                        .addComponent(submitButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(idField)
                        .addComponent(nameField))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel)
                        .addComponent(idField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(nameLabel)
                        .addComponent(nameField))
                .addComponent(submitButton)
        );

        dialog.setVisible(true);
    }

    // Dialog to add a new subject
    private void showAddSubjectDialog() {
        JDialog dialog = new JDialog(this, "Add Subject", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        dialog.add(panel);

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel subjectIdLabel = new JLabel("Subject ID:");
        JTextField subjectIdField = new JTextField(15);

        JLabel nameLabel = new JLabel("Subject Name:");
        JTextField nameField = new JTextField(15);

        JLabel teacherLabel = new JLabel("Teacher Name:");
        JComboBox<String> teacherCombo = new JComboBox<>();
        
        //getAllTeachersWithSubjects method stored to teacher object
        for (Teacher teacher : dbHandler.getAllTeachersWithSubjects()) {
            teacherCombo.addItem(teacher.getName());
        }

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            String subjectId = subjectIdField.getText();
            String name = nameField.getText();
            String selectedTeacherName = (String) teacherCombo.getSelectedItem();

            // تحقق من وجود مادة بنفس ID
            boolean idExists = subjects.stream().anyMatch(subject -> subject.getSubjectId().equals(subjectId));

            if (idExists) {
                JOptionPane.showMessageDialog(dialog, "A subject with this ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return; // إنهاء الدالة إذا كانت المادة موجودة
            }

            Teacher subjectTeacher = dbHandler.getAllTeachersWithSubjects().stream()
                    .filter(t -> t.getName().equalsIgnoreCase(selectedTeacherName))
                    .findFirst()
                    .orElse(null);

            if (subjectTeacher != null) {
                Subject subject = new Subject(subjectId, name, subjectTeacher);
                subjects.add(subject);
                dbHandler.addSubject(subject);
                //JOptionPane.showMessageDialog(dialog, "Subject added successfully!");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Teacher not found!");
            }
        });

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(subjectIdLabel)
                        .addComponent(nameLabel)
                        .addComponent(teacherLabel)
                        .addComponent(submitButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(subjectIdField)
                        .addComponent(nameField)
                        .addComponent(teacherCombo))
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(subjectIdLabel)
                        .addComponent(subjectIdField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(nameLabel)
                        .addComponent(nameField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(teacherLabel)
                        .addComponent(teacherCombo))
                .addComponent(submitButton)
        );

        dialog.setVisible(true);
    }

    // private void enterGrades()Method to enter grades for a student
    // Method to enter grades for a student
// Method to enter a grade for a specific subject for a student
// Method to enter grades for a student
//    private void enterGrades() {
//
//        String studentId = JOptionPane.showInputDialog("Enter Student ID:");
//        Student student = findStudentById(studentId);
//
//        if (student != null) {
//
//            ArrayList<Subject> studentSubjects = student.getSubject(); // تأكد من وجود دالة getSubjects في كلاس Student
//
//            if (studentSubjects.isEmpty()) {
//                JOptionPane.showMessageDialog(this, "The student has no subjects registered!", "No Subjects", JOptionPane.WARNING_MESSAGE);
//                return;
//            }
//
//            JComboBox<String> subjectComboBox = new JComboBox<>();
//            for (Subject subject : studentSubjects) {
//                subjectComboBox.addItem(subject.getName());
//            }
//
//         
//            JPanel panel = new JPanel();
//            panel.add(new JLabel("Select subject:"));
//            panel.add(subjectComboBox);
//
//            int result = JOptionPane.showConfirmDialog(null, panel, "Select Subject", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//
//            if (result == JOptionPane.OK_OPTION) {
//                // استرداد المادة المختارة
//                String selectedSubjectName = (String) subjectComboBox.getSelectedItem();
//                Subject selectedSubject = studentSubjects.stream()
//                        .filter(subject -> subject.getName().equals(selectedSubjectName))
//                        .findFirst()
//                        .orElse(null);
//
//                if (selectedSubject != null) {
//                    // طلب إدخال الدرجة للمادة المختارة
//                    String gradeInput = JOptionPane.showInputDialog("Enter grade for " + selectedSubject.getName() + ":");
//                    try {
//                        double grade = Double.parseDouble(gradeInput);
//                        student.addGrade(new Grade(selectedSubject, grade));
//                        JOptionPane.showMessageDialog(this, "Grade entered successfully for " + selectedSubject.getName() + "!");
//                    } catch (NumberFormatException e) {
//                        JOptionPane.showMessageDialog(this, "Please enter a valid number for the grade.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "Student not found!");
//        }
//    }
 private void enterGrades() {
    String studentId = JOptionPane.showInputDialog("Enter Student ID:");

    // استرجاع المواد المسجلة لهذا الطالب من قاعدة البيانات
    ArrayList<Subject> studentSubjects = dbHandler.getSubjectsForStudent(studentId);
   // studentSubjects.add(new Subject("mohamme","math",new Teacher("21","khn")));

    if (studentSubjects.isEmpty()) {
        JOptionPane.showMessageDialog(null, "The student has no subjects registered or doesn't exist!", "No Subjects", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // إنشاء JComboBox لعرض المواد
    JComboBox<String> subjectComboBox = new JComboBox<>();
    for (Subject subject : studentSubjects) {
        subjectComboBox.addItem(subject.getName()); // فقط عرض اسم المادة
    }

    // إنشاء نافذة لإختيار المادة
    JPanel panel = new JPanel();
    panel.add(new JLabel("Select subject:"));
    panel.add(subjectComboBox);

    int result = JOptionPane.showConfirmDialog(null, panel, "Select Subject", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        String selectedSubjectName = (String) subjectComboBox.getSelectedItem();

        // البحث عن المادة المحددة بناءً على الاسم
        Subject selectedSubject = studentSubjects.stream()
                .filter(subject -> subject.getName().equals(selectedSubjectName))
                .findFirst()
                .orElse(null);

        if (selectedSubject != null) {
            // عرض اسم المعلم في نافذة حوار
            JOptionPane.showMessageDialog(null, "Teacher: " + selectedSubject.getTeacher().getName(), "Teacher Information", JOptionPane.INFORMATION_MESSAGE);

            String gradeInput = JOptionPane.showInputDialog("Enter grade for " + selectedSubject.getName() + ":");

            try {
                double grade = Double.parseDouble(gradeInput);

                // إضافة الدرجة إلى قاعدة البيانات
                dbHandler.addGrade(studentId, selectedSubject.getSubjectId(), grade);

                JOptionPane.showMessageDialog(null, "Grade entered successfully for " + selectedSubject.getName() + "!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for the grade.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}


    

    // Method to search for a student by ID or name
    private void searchStudent() {
        String searchValue = JOptionPane.showInputDialog("Enter Student ID or Name:");
        Student student = findStudentById(searchValue);

        if (student == null) {
            student = findStudentByName(searchValue);
        }

        if (student != null) {
            StringBuilder studentInfo = new StringBuilder();
            studentInfo.append("ID: ").append(student.getStudentId()).append("\n");
            studentInfo.append("Name: ").append(student.getName()).append("\n");
            studentInfo.append("Birth Place: ").append(student.getBirthPlace()).append("\n");
            studentInfo.append("Gender: ").append(student.getGender()).append("\n");
            studentInfo.append("Enrollment Status: ").append(student.getEnrollmentStatus()).append("\n");
            studentInfo.append("Grades: \n");

            for (Grade grade : student.getGrades()) {
                studentInfo.append(grade.getSubject().getName()).append(": ").append(grade.getScore()).append("\n");
            }

            JOptionPane.showMessageDialog(this, studentInfo.toString());
        } else {
            JOptionPane.showMessageDialog(this, "Student not found!");
        }
    }

    // Method to show all students
    private void showAllStudents() {
        JDialog dialog = new JDialog(this, "All Students", true);
        dialog.setSize(600, 300);
        dialog.setLocationRelativeTo(this);

        // تعريف أسماء الأعمدة
        String[] columnNames = {"Student ID", "Name", "Birth Date", "Birth Place", "Gender", "Enrollment Status", "Photo Path"};

        // استخدام دالة getAllStudents من DatabaseHandler لجلب بيانات الطلاب من قاعدة البيانات
        ArrayList<Student> students = dbHandler.getAllStudents();

        // تخزين بيانات الطلاب في مصفوفة لتمريرها إلى JTable
        Object[][] data = new Object[students.size()][7];
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            data[i][0] = student.getStudentId();
            data[i][1] = student.getName();
            data[i][2] = student.getBirthDate();        // تاريخ الميلاد
            data[i][3] = student.getBirthPlace();
            data[i][4] = student.getGender();
            data[i][5] = student.getEnrollmentStatus();
            data[i][6] = student.getPhotoPath();        // مسار الصورة
        }

        // إنشاء الجدول باستخدام البيانات وأسماء الأعمدة
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane);

        dialog.setVisible(true);

    }

//    private void showAllStudents() {
//    JDialog dialog = new JDialog(this, "All Students with Images", true);
//    dialog.setSize(600, 400);
//    dialog.setLocationRelativeTo(this);
//
//    // Define column names
//    String[] columnNames = {"Photo", "Student ID", "Name", "Birth Place", "Gender", "Enrollment Status"};
//
//    // Populate student data for the table
//    Object[][] data = new Object[students.size()][6];
//    for (int i = 0; i < students.size(); i++) {
//        Student student = students.get(i);
//        data[i][0] = new ImageIcon(student.getPhotoPath()); // Load the student's photo
//        data[i][1] = student.getStudentId();
//        data[i][2] = student.getName();
//        data[i][3] = student.getBirthPlace();
//        data[i][4] = student.getGender();
//        data[i][5] = student.getEnrollmentStatus();
//    }
//
//    // Create the table with the data and column names
//    JTable table = new JTable(data, columnNames);
//    
//    // Set custom cell renderer for the photo column
//    table.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
//    
//    // Adjust row height to fit images
//    table.setRowHeight(100);
//
//    JScrollPane scrollPane = new JScrollPane(table);
//    dialog.add(scrollPane);
//
//    dialog.setVisible(true);
//}
    // Method to show students with average grades
    
    private double calculateAverageForStudent(String studentId) {
    String sql = "SELECT AVG(score) AS average FROM grades WHERE studentId = ?";
    double average = 0.0;

    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, studentId); // تحديد ID الطالب
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                average = rs.getDouble("average");
            }
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }

    return average;
}

    
    
    
    
  private void showStudentsWithAvg() {
    JDialog dialog = new JDialog(this, "Students with Averages", true);
    dialog.setSize(500, 300);
    dialog.setLocationRelativeTo(this);

    // Table column names
    String[] columnNames = {"Student ID", "Name", "Average", "Rating"};

    // Get students from the database
    ArrayList<Student> students = dbHandler.getAllStudents();

    // Table data
    String[][] data = new String[students.size()][4];
    for (int i = 0; i < students.size(); i++) {
        Student student = students.get(i);

        // حساب متوسط الدرجات من قاعدة البيانات
        double average = calculateAverageForStudent(student.getStudentId());

        data[i][0] = student.getStudentId();
        data[i][1] = student.getName();
        data[i][2] = String.format("%.2f", average);

        // Calculate rating based on average
        String rating;
        if (average >= 90) {
            rating = "Excellent";
        } else if (average >= 80) {
            rating = "Very Good";
        } else if (average >= 70) {
            rating = "Good";
        } else if (average >= 60) {
            rating = "Pass";
        } else {
            rating = "Faild";
        }

        data[i][3] = rating;
    }

    // Creating JTable with data
    JTable table = new JTable(data, columnNames);
    table.setFillsViewportHeight(true);

    // Adding table to a scroll pane
    JScrollPane scrollPane = new JScrollPane(table);
    dialog.add(scrollPane);

    dialog.setVisible(true);
}

    // Method to display the student's grades with total and average in a table
   private void displayStudentGrades() {
    // طلب الرقم التعريفي للطالب
    String studentId = JOptionPane.showInputDialog("Enter Student ID:");

    if (studentId == null || studentId.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Invalid Student ID!");
        return;
    }

    // الحصول على درجات الطالب من قاعدة البيانات
    ArrayList<Grade> grades = dbHandler.getGradesByStudentId(studentId);

    if (grades.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No grades found for Student ID: " + studentId);
        return;
    }

    // إنشاء جدول لعرض المواد ودرجاتها
    String[] columnNames = {"Subject", "Grade"};
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
    JTable table = new JTable(tableModel);

    double total = 0;

    for (Grade grade : grades) {
        String subjectName = grade.getSubject().getName();
        double gradeValue = grade.getScore();
        total += gradeValue;
        tableModel.addRow(new Object[]{subjectName, gradeValue});
    }

    // حساب المجموع والمعدل
    double average = grades.size() > 0 ? total / grades.size() : 0;

    // إضافة صف المجموع
    tableModel.addRow(new Object[]{"Total", total});
    // إضافة صف المعدل
    tableModel.addRow(new Object[]{"Average", average});

    // عرض النتائج في نافذة منبثقة
    JScrollPane scrollPane = new JScrollPane(table);
    JOptionPane.showMessageDialog(null, scrollPane, "Student Results for ID: " + studentId, JOptionPane.INFORMATION_MESSAGE);
}

    private void displayTopThreeStudents() {
        // قائمة للتأكد من وجود طلاب في النظام
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students found!");
            return;
        }

        // ترتيب الطلاب بناءً على المعدل بشكل تنازلي
        ArrayList<Student> sortedStudents = new ArrayList<>(students);
        sortedStudents.sort((s1, s2) -> Double.compare(s2.calculateAverage(), s1.calculateAverage()));

        // إعداد النص لعرض الثلاثة الأوائل
        StringBuilder topStudentsMessage = new StringBuilder("Top 3 Students:\n");
        for (int i = 0; i < Math.min(3, sortedStudents.size()); i++) {
            Student student = sortedStudents.get(i);
            topStudentsMessage.append((i + 1))
                    .append(". Name: ").append(student.getName())
                    .append(", Average: ").append(String.format("%.2f", student.calculateAverage()))
                    .append("\n");
        }

        // عرض النتائج في نافذة منبثقة
        JOptionPane.showMessageDialog(null, topStudentsMessage.toString(), "Top 3 Students", JOptionPane.INFORMATION_MESSAGE);
    }

    //Delete function to Delete Student by id
    private void deleteStudent() {
        String studentId = JOptionPane.showInputDialog("Enter Student ID or Name:");
dbHandler.deleteStudent(studentId);
    }//end of delete Student method

    private void deleteTeacher() {
String teacherId = JOptionPane.showInputDialog("Enter Teacher ID:");
        if (teacherId != null && !teacherId.trim().isEmpty()) {
            dbHandler.deleteTeacherById(teacherId);  // استدعاء دالة الحذف في DatabaseHandler
        } else {
            JOptionPane.showMessageDialog(null, "Teacher ID cannot be empty!");
        }
    }//end of delete teacher method

//private void showStatistics() {
//    JDialog dialog = new JDialog(this, "System Statistics", true);
//    dialog.setSize(500, 400);
//    dialog.setLocationRelativeTo(this);
//
//    // جلب البيانات من قاعدة البيانات
//    ArrayList<Student> students = dbHandler.getAllStudents();
//    ArrayList<Teacher> teachers = dbHandler.getAllTeachersWithSubjects();
//    ArrayList<Subject> subjects = dbHandler.getAllSubjects();
//
//    // حساب الإحصائيات
//    int totalStudents = students.size();
//    int totalSubjects = subjects.size();
//    int totalTeachers = teachers.size();
//    double totalAverage = 0;
//    int excellentCount = 0, veryGoodCount = 0, goodCount = 0, passCount = 0, failCount = 0;
//    double highestAverage = Double.MIN_VALUE;
//    double lowestAverage = Double.MAX_VALUE;
//
//    for (Student student : students) {
//        // جلب درجات الطالب من قاعدة البيانات وحساب المعدل
//        ArrayList<Grade> grades = dbHandler.getGradesByStudentId(student.getStudentId());
//        double average = calculateAverage(grades);
//
//        totalAverage += average;
//
//        // تحديث أعلى وأدنى معدل
//        if (average > highestAverage) {
//            highestAverage = average;
//        }
//        if (average < lowestAverage) {
//            lowestAverage = average;
//        }
//
//        // تصنيف الطلاب بناءً على المعدل
//        if (average >= 90) {
//            excellentCount++;
//        } else if (average >= 80) {
//            veryGoodCount++;
//        } else if (average >= 70) {
//            goodCount++;
//        } else if (average >= 60) {
//            passCount++;
//        } else {
//            failCount++;
//        }
//    }
//
//    // حساب المعدل الكلي للطلاب
//    double averageOfAverages = totalStudents > 0 ? totalAverage / totalStudents : 0;
//
//    // تجهيز بيانات الجدول
//    String[][] data = {
//        {"Total Students", String.valueOf(totalStudents)},
//        {"Total Subjects", String.valueOf(totalSubjects)},
//        {"Total Teachers", String.valueOf(totalTeachers)},
//        {"Average of All Students", String.format("%.2f", averageOfAverages)},
//        {"Highest Average", String.format("%.2f", highestAverage)},
//        {"Lowest Average", String.format("%.2f", lowestAverage)},
//        {"Excellent Students (90+)", String.valueOf(excellentCount)},
//        {"Very Good Students (80-89)", String.valueOf(veryGoodCount)},
//        {"Good Students (70-79)", String.valueOf(goodCount)},
//        {"Pass Students (60-69)", String.valueOf(passCount)},
//        {"Failing Students (<60)", String.valueOf(failCount)},};
//
//    // أسماء الأعمدة
//    String[] columnNames = {"Statistic", "Value"};
//
//    // إنشاء جدول باستخدام البيانات
//    JTable table = new JTable(data, columnNames);
//    table.setFillsViewportHeight(true);
//
//    // إضافة الجدول إلى نافذة منبثقة
//    JScrollPane scrollPane = new JScrollPane(table);
//    dialog.add(scrollPane);
//
//    dialog.setVisible(true);
//}
    
    
    
    
    private void showStatistics() {
    JDialog dialog = new JDialog(this, "System Dashboard", true);
    dialog.setSize(800, 600);
    dialog.setLocationRelativeTo(this);
    dialog.setLayout(new GridLayout(2, 2)); // تصميم الشبكة لعرض الرسوم البيانية

    // جلب البيانات من قاعدة البيانات
    ArrayList<Student> students = dbHandler.getAllStudents();
    ArrayList<Teacher> teachers = dbHandler.getAllTeachersWithSubjects();
    ArrayList<Subject> subjects = dbHandler.getAllSubjects();

    int totalStudents = students.size();
    int totalSubjects = subjects.size();
    int totalTeachers = teachers.size();

    int excellentCount = 0, veryGoodCount = 0, goodCount = 0, passCount = 0, failCount = 0;

    for (Student student : students) {
        ArrayList<Grade> grades = dbHandler.getGradesByStudentId(student.getStudentId());
        double average = calculateAverage(grades);

        if (average >= 90) {
            excellentCount++;
        } else if (average >= 80) {
            veryGoodCount++;
        } else if (average >= 70) {
            goodCount++;
        } else if (average >= 60) {
            passCount++;
        } else {
            failCount++;
        }
    }

    // الرسم 1: مخطط دائري لتوزيع الطلاب حسب الدرجات
    DefaultPieDataset gradeDataset = new DefaultPieDataset();
    gradeDataset.setValue("Excellent (90+)", excellentCount);
    gradeDataset.setValue("Very Good (80-89)", veryGoodCount);
    gradeDataset.setValue("Good (70-79)", goodCount);
    gradeDataset.setValue("Pass (60-69)", passCount);
    gradeDataset.setValue("Fail (<60)", failCount);

    JFreeChart gradeChart = ChartFactory.createPieChart("Student Grade Distribution", gradeDataset, true, true, false);
    dialog.add(new ChartPanel(gradeChart));

    // الرسم 2: مخطط شريطي لعدد الطلاب والمعلمين والمواد
    DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
    categoryDataset.addValue(totalStudents, "Count", "Students");
    categoryDataset.addValue(totalSubjects, "Count", "Subjects");
    categoryDataset.addValue(totalTeachers, "Count", "Teachers");

    JFreeChart categoryChart = ChartFactory.createBarChart("Counts Overview", "Category", "Count", categoryDataset);
    dialog.add(new ChartPanel(categoryChart));

    // الرسم 3: مخطط شريطي لتوزيع الطلاب حسب الأداء
    DefaultCategoryDataset performanceDataset = new DefaultCategoryDataset();
    performanceDataset.addValue(excellentCount, "Students", "Excellent (90+)");
    performanceDataset.addValue(veryGoodCount, "Students", "Very Good (80-89)");
    performanceDataset.addValue(goodCount, "Students", "Good (70-79)");
    performanceDataset.addValue(passCount, "Students", "Pass (60-69)");
    performanceDataset.addValue(failCount, "Students", "Fail (<60)");

    JFreeChart performanceChart = ChartFactory.createBarChart("Performance Distribution", "Performance", "Number of Students", performanceDataset);
    dialog.add(new ChartPanel(performanceChart));

    // الرسم 4: مخطط دائري يوضح النسب بين المعلمين والمواد
    DefaultPieDataset teacherSubjectDataset = new DefaultPieDataset();
    teacherSubjectDataset.setValue("Teachers", totalTeachers);
    teacherSubjectDataset.setValue("Subjects", totalSubjects);

    JFreeChart teacherSubjectChart = ChartFactory.createPieChart("Teachers vs Subjects", teacherSubjectDataset, true, true, false);
    dialog.add(new ChartPanel(teacherSubjectChart));

    // عرض النافذة
    dialog.setVisible(true);
}

// دالة لحساب معدل الدرجات

   
    
    
    
    
    
    
    

// دالة لحساب معدل الدرجات
private double calculateAverage(ArrayList<Grade> grades) {
    if (grades.isEmpty()) {
        return 0;
    }
    double totalScore = 0;
    for (Grade grade : grades) {
        totalScore += grade.getScore();
    }
    return totalScore / grades.size();
}


    // Helper method to find a student by ID
    private Student findStudentById(String studentId) {
        for (Student student :  dbHandler.getAllStudents()) {
            if (student.getStudentId().equalsIgnoreCase(studentId)) {
                return student;
            }
        }
        return null;
    }

    // Helper method to find a student by name
    private Student findStudentByName(String name) {
        for (Student student : dbHandler.getAllStudents()) {
            if (student.getName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }
//Helper method to find teacher by id

    private Teacher findTeacherById(String teacherId) {

        for (Teacher teacher : teachers) {
            if (teacher.getTeacherId().equalsIgnoreCase(teacherId)) {
                return teacher;
            }

        }
        return null;

    }
    
    
    private void showHowToUse() {
    file.displayFileContent(U_F1);
    
}

// دالة عرض فوائد التطبيق
private void showBenefits() {
   file.displayFileContent(U_F2);
}











    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementApp().setVisible(true));
    }
}
