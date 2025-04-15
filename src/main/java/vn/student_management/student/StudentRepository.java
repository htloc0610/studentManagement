package vn.student_management.student;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

    @Repository
    public interface StudentRepository extends JpaRepository<Student, Integer> {
    }
