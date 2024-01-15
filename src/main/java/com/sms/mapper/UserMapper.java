package com.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sms.dto.UserSelectDto;
import com.sms.entity.User;
import com.sms.vo.UseLoginVo;
import com.sms.vo.UserPageVo;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ssnow
 * @since 2024-01-14
 */
public interface UserMapper extends BaseMapper<User> {

    Page<UserPageVo> selectUserLoginVoPage(@Param("page") Page<UserPageVo> voPage, @Param("userSelectDto")UserSelectDto userSelectDto);
}
