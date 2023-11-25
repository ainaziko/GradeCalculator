package whz.pti.eva.gradecalculator.grade.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import whz.pti.eva.gradecalculator.grade.domain.Grade;
import whz.pti.eva.gradecalculator.grade.service.GradeService;

import java.util.List;

@Controller
public class GradeController {

    private final GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @RequestMapping("/grades")
    public String listAllGrades(Model model) {
        List<Grade> list = gradeService.listAllGrades();
        model.addAttribute("listAllGrades", list);
        model.addAttribute("average", gradeService.calculateAverage());
        model.addAttribute("gradeAdd", new Grade());
        return "grades";
    }

    @PostMapping("/add")
    public String createNewGrade(@RequestParam String lecture, @RequestParam String gradeNote) {
        gradeService.addGrade(lecture, gradeNote);
        return "redirect:/grades?lecture=" + lecture + "&gradeNote=" + gradeNote;
    }


}
