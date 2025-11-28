package com.example.ExcelDownload.Services;

import com.example.ExcelDownload.Entity.Student;
import com.example.ExcelDownload.Exceptions.RecordNotFoundException;
import com.example.ExcelDownload.Repository.Studentrepo;
import com.example.ExcelDownload.Utility.Helper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class StudentService {

    @Autowired
    Studentrepo studentrepo;

    public Student saveStudent(Student student){
        return studentrepo.save(student);
    }

    public Page<Student> getAllStudents(int page , int size){
        Pageable pageable= PageRequest.of(page, size);

        return studentrepo.findAll(pageable);
    }

    public List<Student> getAllStudentSorted()
    {
         return studentrepo.findAll().stream()
                 .sorted((o1,o2)-> o1.getStandard().compareTo(o2.getStandard()))
                 .collect(Collectors.toList());
    }



    public void deleteStudent(Integer id){
        studentrepo.deleteById(id);
    }

    public Student updateStudent(Student student){
        Student existingStudent = studentrepo.findById(student.getId()).orElse(null);
        if (existingStudent != null) {
            existingStudent.setFirstname(student.getFirstname());
            existingStudent.setLastname(student.getLastname());
            existingStudent.setStandard(student.getStandard());
            return studentrepo.save(existingStudent);
        }
        return null;
    }

    public Student getStudentById(Integer id){
        return studentrepo.findById(id).orElseThrow(() -> new RecordNotFoundException("Student not found with id: " + id));
    }

    public List<Student> getStudentsByStandard(String standard){
        return studentrepo.getByStandard(standard);
    }

    public ByteArrayInputStream getalldata() throws IOException {
        List<Student> alldata= studentrepo.findAll();
        ByteArrayInputStream byteArrayInputStream = Helper.dataToExcel(alldata);
        return byteArrayInputStream;
    }



}
