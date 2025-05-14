package tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import tests.model.Student;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;

public class JsonFileParseTest {

    File json = new File("src/test/resources/student.json");
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void jsonTest() throws Exception {
        JsonNode jsonNode = objectMapper.readValue(json, JsonNode.class);
        assertThat(jsonNode.get("name").asText()).isEqualTo("Katerina");
        assertThat(jsonNode.get("isGoodStudent").asBoolean()).isTrue();
        assertThat(jsonNode.get("passport").get("number").asInt()).isEqualTo(123456);
    }

    @Test
    void jsonTestWithModel() throws Exception {
        Student student = objectMapper.readValue(json, Student.class);
        assertThat(student.age).isEqualTo(35);
        assertThat(student.lessons.get(1)).isEqualTo("Files");
        assertThat(student.passport.issueDate).isEqualTo("12.12.2022");
    }
}