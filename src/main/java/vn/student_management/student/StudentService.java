package vn.student_management.student;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.*;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(int id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(int id, Student student) {
        Student existingStudent = getStudentById(id);
        existingStudent.setStudentName(student.getStudentName());
        existingStudent.setStudentCode(student.getStudentCode());
        existingStudent.setStudentInfo(student.getStudentInfo());
        return studentRepository.save(existingStudent);
    }

    public void deleteStudent(int id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }
}
