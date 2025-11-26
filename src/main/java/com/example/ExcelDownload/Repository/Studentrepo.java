package com.example.ExcelDownload.Repository;

import com.example.ExcelDownload.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Studentrepo extends JpaRepository<Student, Integer> {

    public List<Student> getByStandard(String standard);
}
