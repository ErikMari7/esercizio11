package co.develhope.Esercizio11;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/post")
    public @ResponseBody Student createNewStudent(@RequestBody Student student){
       return studentRepository.save(student);
    }
    @GetMapping("/getall")
    public List<Student> getStudents (){
        return studentRepository.findAll();
    }
    @GetMapping("/get/{id}")
    public Student getStudent(@PathVariable Long id){
        Optional<Student> student = studentRepository.findById(id);
        if(student.isPresent()){
            return student.get();
        } else {
            return null;
        }
    }
    @PutMapping("/changeid/{id}")
    public Student changeId(@PathVariable Long id, @RequestBody Student student){
        student.setId(id);
        return studentRepository.save(student);
    }
    @PutMapping("/changeisworking/{id}")
    public Student changeIsWorking(@PathVariable Long id,@RequestParam boolean isWorking){
        return studentService.changeValueOfIsWorking(id,isWorking);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable Long id){
        studentRepository.deleteById(id);
    }
}
