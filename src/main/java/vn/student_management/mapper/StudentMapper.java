//package vn.student_management.mapper;
//
//import org.springframework.stereotype.Component;
//import vn.student_management.student.Student;
//import vn.student_management.student.StudentRequestDTO;
//import vn.student_management.student.StudentResponseDTO;
//import vn.student_management.studentInfor.StudentInfo;
//import vn.student_management.studentInfor.StudentInfoRequestDTO;
//import vn.student_management.studentInfor.StudentInfoResponseDTO;
//
//
//@Component
//public class StudentMapper {
//
//    public Student toStudent(StudentRequestDTO request) {
//        if (request == null) return null;
//
//        Student student = new Student();
//        student.setStudentName(request.getStudentName());
//
//        if (request.getStudentInfo() != null) {
//            StudentInfo info = toStudentInfo(request.getStudentInfo());
//            student.setStudentInfo(info);
//            info.setStudent(student); // nếu có mối quan hệ 2 chiều
//        }
//
//        return student;
//    }
//
//    public StudentResponseDTO toStudentResponse(Student student) {
//        if (student == null) return null;
//
//        StudentResponseDTO dto = new StudentResponseDTO();
//        dto.setStudentId(student.getStudentId());
//        dto.setStudentName(student.getStudentName());
//        dto.setCreatedAt(student.getCreatedAt());
//        dto.setUpdatedAt(student.getUpdatedAt());
//
//        if (student.getStudentInfo() != null) {
//            dto.setStudentInfo(toStudentInfoResponse(student.getStudentInfo()));
//        }
//
//        return dto;
//    }
//
//    public StudentInfo toStudentInfo(StudentInfoRequestDTO request) {
//        if (request == null) return null;
//
//        StudentInfo info = new StudentInfo();
//        info.setAddress(request.getAddress());
//        info.setAverageScore(request.getAverageScore());
//        info.setDateOfBirth(request.getDateOfBirth());
//
//        return info;
//    }
//
//    public StudentInfoResponseDTO toStudentInfoResponse(StudentInfo info) {
//        if (info == null) return null;
//
//        StudentInfoResponseDTO dto = new StudentInfoResponseDTO();
//        dto.setInfoId(info.getInfoId());
//        dto.setAddress(info.getAddress());
//        dto.setAverageScore(info.getAverageScore());
//        dto.setDateOfBirth(info.getDateOfBirth());
//        dto.setCreatedAt(info.getCreatedAt());
//        dto.setUpdatedAt(info.getUpdatedAt());
//
//        return dto;
//    }
//}
//
