package com.sms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("sms_class")
@ApiModel(value = "Class对象", description = "")
public class Class extends BaseEntity {

    @TableField("class_code")
    private String classCode;

    @TableField("class_college")
    private String classCollege;

    @TableField("class_description")
    private String classDescription;

    @TableField("head_teacher_id")
    private Long headTeacherId;
}
