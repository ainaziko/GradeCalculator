package whz.pti.eva.gradecalculator;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import whz.pti.eva.gradecalculator.grade.domain.Grade;
import whz.pti.eva.gradecalculator.grade.service.GradeService;

import java.util.List;


@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest
public class GradeControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @MockBean
    private GradeService gradeService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();
        when(gradeService.calculateAverage()).thenReturn(1.5);
        when(gradeService.listAllGrades()).thenReturn(List.of(new Grade(), new Grade()));
    }

    @Test
    public void testListAllGrades() throws Exception {
        mockMvc.perform(get("/grades")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("grades"))
                .andExpect(model().attribute("listAllGrades", hasSize(2))) //in db 2
                .andDo(print());;
    }


    @Test
    public void addGrade() throws Exception {
        mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("lecture", "Matematik")
                .param("gradeNote", "1.5")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/grades?lecture=Matematik&gradeNote=1.5"));
    }


}
