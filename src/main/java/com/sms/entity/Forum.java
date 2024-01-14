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
@TableName("sms_forum")
@ApiModel(value = "Forum对象", description = "")
public class Forum extends BaseEntity {

    @TableField("parent_id")
    private Long parentId;

    @TableField("is_anonymous")
    private Boolean isAnonymous;

    @TableField("content")
    private String content;

    @TableField("likes")
    private Integer likes;
}
