package vn.student_management.mapper;

import org.mapstruct.*;
import vn.student_management.student.Student;
import vn.student_management.student.StudentRequestDTO;
import vn.student_management.student.StudentResponseDTO;
import vn.student_management.studentInfor.StudentInfo;
import vn.student_management.studentInfor.StudentInfoRequestDTO;
import vn.student_management.studentInfor.StudentInfoResponseDTO;


@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student toStudent(StudentRequestDTO request);
    StudentResponseDTO toStudentResponse(Student student);

    StudentInfo toStudentInfo(StudentInfoRequestDTO request);
    StudentInfoResponseDTO toStudentInfoResponse(StudentInfo info);
}

