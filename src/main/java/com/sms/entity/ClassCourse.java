package com.sms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author ssnow
 * @since 2024-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sms_class_course")
@ApiModel(value = "ClassCourse对象", description = "")
public class ClassCourse extends BaseEntity {

    @TableField("course_id")
    private Long courseId;

    @TableField("class_id")
    private Long classId;
}
