/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package studentmanagementapp;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;

public class FileViewer {

    public void displayFileContent(String filePath) {
        // إنشاء نافذة لعرض المحتوى
        JDialog dialog = new JDialog((Frame) null, "File Viewer", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);

        // إنشاء JTextArea لعرض النصوص
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false); // جعل النص غير قابل للتعديل
        JScrollPane scrollPane = new JScrollPane(textArea);

        // محاولة قراءة الملف
        try {
            // قراءة المحتوى من الملف
            String content = Files.readString(Paths.get(filePath));
            textArea.setText(content); // إضافة النصوص إلى JTextArea
        } catch (IOException e) {
            // عرض رسالة خطأ إذا حدثت مشكلة أثناء القراءة
            JOptionPane.showMessageDialog(dialog, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // إضافة JTextArea إلى النافذة
        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        // مثال على استدعاء الدالة
        FileViewer viewer = new FileViewer();
        viewer.displayFileContent("path/to/your/file.txt"); // استبدل بالمسار الفعلي للملف
    }
}
