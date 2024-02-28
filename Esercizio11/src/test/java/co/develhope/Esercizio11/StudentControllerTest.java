package co.develhope.Esercizio11;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class StudentControllerTest {
    @Autowired
    private StudentController studentController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoad(){
        assertThat(studentController).isNotNull();
    }

    private Student createStudent()throws Exception{
        Student student = new Student();
        student.setId(1L);
        student.setName("Erik");
        student.setSurname("Marigliano");
        student.setWorking(true);
        String studentJson = objectMapper.writeValueAsString(student);

        MvcResult result = this.mockMvc.perform(post("/student/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(studentJson))
                .andExpect(status().isOk())
                .andReturn();
        return objectMapper.readValue(result.getResponse().getContentAsString(),Student.class);
    }
    private Student getStudentFromId(Long id) throws Exception{
        MvcResult result =this.mockMvc.perform(get("/student/get/"+ id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        try {
            String studentJson = result.getResponse().getContentAsString();
            return objectMapper.readValue(studentJson, Student.class);
        } catch (Exception e){
            return null;
        }
    }

    @Test
    void createStudentTest() throws  Exception{
        Student student = createStudent();
        assertThat(student.getId()).isNotNull();
    }
    @Test
    void getStudentByIdTest() throws  Exception{
        Student student = createStudent();
        assertThat(student.getId()).isNotNull();
        Student studentResponse = getStudentFromId(student.getId());
        assertThat(studentResponse.getId()).isEqualTo(student.getId());
    }
    @Test
    void deleteStudent() throws Exception{
        Student student = createStudent();
        assertThat(student.getId()).isNotNull();

        this.mockMvc.perform(delete("/student/delete/" + student.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Student studentFromResponseGet = getStudentFromId(student.getId());
        assertThat(studentFromResponseGet).isNull();
    }
    @Test
    void updateStudentWorkingTest() throws Exception{
        Student student = createStudent();
        assertThat(student.getId()).isNotNull();

        MvcResult result = this.mockMvc.perform(put("/student/changeisworking/" + student.getId() + "?isWorking=false"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Student studentFromResponsePut = objectMapper.readValue(result.getResponse().getContentAsString(),Student.class);
        assertThat(studentFromResponsePut.getId()).isEqualTo(student.getId());
        assertThat(studentFromResponsePut.isWorking()).isEqualTo(false);
    }
}
