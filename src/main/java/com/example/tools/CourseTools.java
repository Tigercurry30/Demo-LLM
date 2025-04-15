package com.example.tools;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.example.entity.po.Course;
import com.example.entity.po.CourseReservation;
import com.example.entity.po.School;
import com.example.entity.query.CourseQuery;
import com.example.service.ICourseReservationService;
import com.example.service.ICourseService;
import com.example.service.ISchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CourseTools {

    private final ICourseService courseService;
    private final ICourseReservationService courseReservationService;
    private final ISchoolService schoolService;

    @Tool(description = "根据条件查询课程")
    public List<Course> queryCourse(@ToolParam(description = "查询的条件", required = false) CourseQuery query) {

        if(query == null){
            return courseService.list();
        }
        QueryChainWrapper<Course> wrapper = courseService.query()
                .eq(query.getType() != null, "type", query.getType())   // type = '编程'
                .le(query.getEdu() != null, "edu", query.getEdu());     // edu <= 2

        if(query.getSorts() != null && !query.getSorts().isEmpty()){
            for(CourseQuery.Sort sort : query.getSorts()){
                wrapper.orderBy(true, sort.getAsc(), sort.getField());
            }
        }
        return wrapper.list();

    }


    //下一步在school表中添加course字段，根据查询到的course查询校区
    @Tool(description = "查询所有校区")
    public List<School> querySchool() {
        return schoolService.list();
    }

    @Tool(description = "生成预约单，返回预约单号")
    public Integer createCourseReservation(@ToolParam(description = "预约课程") String course,
                                           @ToolParam(description = "预约校区") String school,
                                           @ToolParam(description = "学生姓名") String studentName,
                                           @ToolParam(description = "联系方式") String contactInfo,
                                           @ToolParam(description = "备注", required = false) String remark) {

        CourseReservation courseReservation = new CourseReservation();
        courseReservation.setCourse(course);
        courseReservation.setSchool(school);
        courseReservation.setStudentName(studentName);
        courseReservation.setContactInfo(contactInfo);
        courseReservation.setRemark(remark);
        courseReservationService.save(courseReservation);

        return courseReservation.getId();
    }



























}
