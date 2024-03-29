package co.develhope.Esercizio11;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Student changeValueOfIsWorking(Long id,boolean isWorking){
        Optional<Student> student = studentRepository.findById(id);
        if(student.isPresent()){
            Student newStudent = student.get();
            newStudent.setWorking(isWorking);
            return studentRepository.save(newStudent);
        }
        return null;

    }
}
