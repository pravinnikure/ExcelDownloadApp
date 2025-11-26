package com.example.ExcelDownload.Controller;

import com.example.ExcelDownload.Entity.Student;
import com.example.ExcelDownload.Exceptions.RecordNotFoundException;
import com.example.ExcelDownload.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/students")
//@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Content-Disposition")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping()
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student saved = studentService.saveStudent(student);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Student>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Student> result = studentService.getAllStudents(page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(studentService.getStudentById(id));
        } catch (RecordNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Integer id, @RequestBody Student student) {
        student.setId(id);
        Student updated = studentService.updateStudent(student);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/standard/{standard}")
    public ResponseEntity<List<Student>> getStudentsByStandard(@PathVariable String standard) {
        return ResponseEntity.ok(studentService.getStudentsByStandard(standard));
    }

    @GetMapping("/excelDownload")
    public ResponseEntity<Resource> download() throws IOException {

        String filename="StudentData.xlsx";

        ByteArrayInputStream data= studentService.getalldata();
        InputStreamResource file = new InputStreamResource(data);

        ResponseEntity<Resource> body = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);

        return body;

    }
}
