package com.sms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

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
@TableName("sms_grade")
@ApiModel(value = "Grade对象", description = "")
public class Grade extends BaseEntity {

    @TableField("regular_score")
    private BigDecimal regularScore;

    @TableField("exam_score")
    private BigDecimal examScore;

    @TableField("pass_threshold")
    private BigDecimal passThreshold;
}
