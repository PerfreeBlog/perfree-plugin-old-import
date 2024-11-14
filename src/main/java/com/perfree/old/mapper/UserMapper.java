package com.perfree.old.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.perfree.commons.mapper.BaseMapperX;
import com.perfree.old.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author perfree
 * @since 2023-09-27
 */
@Mapper
public interface UserMapper extends BaseMapperX<User> {


    default User queryByAccount(String account){
        return selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getAccount, account)
        );
    }

}
