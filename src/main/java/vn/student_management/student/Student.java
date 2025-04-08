package vn.student_management.student;

import jakarta.persistence.*;
import lombok.*;
import vn.student_management.studentInfor.StudentInfo;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "student_name", length = 20, nullable = false)
    private String studentName;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private StudentInfo studentInfo;

}