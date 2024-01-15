package com.sms.vo;

import lombok.Data;

@Data
public class UserPageVo {
    private Long id;
    private String account;//账号
    private String name;//真实姓名
    private String location;//籍贯
    private String email;//邮箱
    private Integer gender;//性别
    private Integer age;//年龄
    private String phone;//手机号码
    private String idCard;//身份证号码
    private Integer role;//权限等级 0管理员 1教师 2学生
    private Boolean isBan;//是否禁用
    private String avatar;//头像图片的名称
}
