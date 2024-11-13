package com.perfree.old.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.perfree.commons.mapper.BaseMapperX;
import com.perfree.old.model.Attach;
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
public interface AttachMapper extends BaseMapperX<Attach> {


    default Attach queryByPath(String path){
        return selectOne(new LambdaQueryWrapper<Attach>()
                .eq(Attach::getPath, path)
        );
    }

}
