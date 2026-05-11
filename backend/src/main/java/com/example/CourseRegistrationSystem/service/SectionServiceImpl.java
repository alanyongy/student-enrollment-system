package com.example.CourseRegistrationSystem.service;

import com.example.CourseRegistrationSystem.dao.EnrollmentDAO;
import com.example.CourseRegistrationSystem.dao.SectionDAO;
import com.example.CourseRegistrationSystem.dto.EnrollmentResponseDTO;
import com.example.CourseRegistrationSystem.entity.Course;
import com.example.CourseRegistrationSystem.entity.Instructor;
import com.example.CourseRegistrationSystem.entity.Section;
import com.example.CourseRegistrationSystem.entity.Semester;
import com.example.CourseRegistrationSystem.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionDAO sectionDAO;

    @Autowired
    private CourseService courseService;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private EnrollmentDAO enrollmentDAO;

    private SemesterService semesterService;

    public SectionServiceImpl(CourseService courseService, SemesterService semesterService){
        this.courseService = courseService;
        this.semesterService = semesterService;
    }

    @Override
    public List<Section> getAllSections(int page, int size, String sortBy, String direction) {
        return sectionDAO.findAll(page, size, sortBy, direction);
    }

    @Override
    public Section getSectionById(Long id) {
        Section section = sectionDAO.findById(id);
        if (section == null) {
            throw new ResourceNotFoundException("Section not found with id: " + id);
        }
        return section;
    }

    @Override
    public Section createSection(Section section) {
        return sectionDAO.save(section);
    }

    @Override
    public Section updateSection(Long id, Section req) {

        Section existing = sectionDAO.findById(id);
        Course course = courseService.getCourse(req.getCourse().getCourseId());
        Semester semester = semesterService.getSemesterById(req.getSemester().getSemesterId());

        existing.setCourse(course);
        existing.setSemester(semester);
        existing.setScheduleTime(req.getScheduleTime());
        existing.setLocation(req.getLocation());
        existing.setCapacity(req.getCapacity());

        if (req.getInstructor() != null) {
            Instructor instructor = instructorService.getInstructor(req.getInstructor().getPersonId());
            existing.setInstructor(instructor);
        } else {
            existing.setInstructor(null); // optional, if you allow removing instructor
        }

        return sectionDAO.save(existing);
    }

    @Override
    public void deleteSection(Long id) {
        sectionDAO.delete(id);
    }

    @Override
    public void assignInstructor(Long sectionId, Long instructorId) {
        sectionDAO.assignInstructor(sectionId, instructorId);
    }

    @Override
    public void removeInstructor(Long sectionId) {
        sectionDAO.removeInstructor(sectionId);
    }

    @Override
    public void assignCourse(Long sectionId, Long courseId) {
        Section section = getSectionById(sectionId);
        Course course = courseService.getCourse(courseId);
        section.setCourse(course);
        sectionDAO.update(section);
    }

    @Override
    public void removeCourse(Long sectionId, Long courseId) {
        Section section = getSectionById(sectionId);
        Course course = courseService.getCourse(courseId);
        section.setCourse(null);
        sectionDAO.update(section);
    }

    @Override
    public List<Long> getCoursesForSection(Long sectionId) {
        Section section = getSectionById(sectionId);
        if (section.getCourse() != null) {
            return List.of(section.getCourse().getCourseId());
        }
        return List.of();
    }

    @Override
    public List<EnrollmentResponseDTO> getAvailableSectionsForSemester(Long semesterId) {
        List<Section> sections = sectionDAO.findBySemesterId(semesterId);
        List<EnrollmentResponseDTO> details = new ArrayList<>();

        for (Section section : sections) {
            int enrolledCount = Math.toIntExact(enrollmentDAO.countBySection(section.getSectionId()));

            System.out.println("Section: " + section.getSectionId() +
                    " | Enrolled: " + enrolledCount +
                    " | Capacity: " + section.getCapacity());

            if (section.getCapacity() != null && enrolledCount >= section.getCapacity()) {
                System.out.println("Skipping full section: " + section.getSectionId());
                continue;
            }

            EnrollmentResponseDTO dto = new EnrollmentResponseDTO();
            dto.setSectionId(section.getSectionId());
            dto.setEnrollmentId(null);
            if (section.getCourse() != null) {
                dto.setCourseCode(section.getCourse().getCourseNumber());
                dto.setCourseName(section.getCourse().getDescription());
                dto.setCredits(section.getCourse().getCredits());
            }
            dto.setSectionLabel(buildSectionLabel(section));
            dto.setSemester(section.getSemester() != null ? section.getSemester().getTermName() : null);
            dto.setInstructor(buildInstructorName(section.getInstructor()));
            dto.setLocation(section.getLocation());
            dto.setCapacity(section.getCapacity());
            dto.setEnrolledCount(enrolledCount);
            details.add(dto);
        }

        return details;
    }

    private String buildSectionLabel(Section section) {
        return "Section " + section.getSectionId();
    }

    private String buildInstructorName(Instructor instructor) {
        if (instructor == null) {
            return null;
        }

        String firstName = instructor.getFirstName();
        String lastName = instructor.getLastName();

        if (firstName == null && lastName == null) {
            return null;
        }
        if (firstName == null) {
            return lastName;
        }
        if (lastName == null) {
            return firstName;
        }
        return firstName + " " + lastName;

    }
}
