package whz.pti.eva.gradecalculator.grade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import whz.pti.eva.gradecalculator.grade.domain.Grade;
import whz.pti.eva.gradecalculator.grade.domain.GradeRepository;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService{

    private final GradeRepository gradeRepository;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public List<Grade> listAllGrades() {
        return gradeRepository.findAll();
    }

    @Override
    public void addGrade(String lecture, String gradeNote) {
        gradeRepository.save(new Grade(lecture, gradeNote));
    }

    @Override
    public double calculateAverage() {
        List<Grade> grades = listAllGrades();

        return grades.stream()
                .mapToDouble(grade -> Double.parseDouble(grade.getGradeNote()))
                .average()
                .orElse(0.0);
    }
}
