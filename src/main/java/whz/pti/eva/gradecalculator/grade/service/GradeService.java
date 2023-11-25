package whz.pti.eva.gradecalculator.grade.service;

import whz.pti.eva.gradecalculator.grade.domain.Grade;

import java.util.List;

public interface GradeService {

    List<Grade> listAllGrades();

    void addGrade(String lecture, String gradeNote);

    double calculateAverage();

}
