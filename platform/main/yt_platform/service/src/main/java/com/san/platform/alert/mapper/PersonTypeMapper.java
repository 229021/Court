package com.san.platform.alert.mapper;

import com.san.platform.alert.PersonType;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PersonTypeMapper extends Mapper<PersonType> {
    /**
     * 根据typeid查询内容
     * @param typeid
     * @return
     */
    public PersonType Getbyid(Integer typeid);


    public List<PersonType> queryCustomize(Integer typeSign);

    public PersonType selectPersonTypeByTypeId(PersonType personType);

}
