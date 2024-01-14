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
@TableName("sms_course")
@ApiModel(value = "Course对象", description = "")
public class Course extends BaseEntity {

    @TableField("is_elective")
    private Boolean isElective;

    @TableField("credits")
    private Integer credits;

    @TableField("teacher_id")
    private Long teacherId;
}
