package com.jiangzuoyoupin.mapper;

import com.jiangzuoyoupin.domain.InvitationCode;
import org.apache.ibatis.annotations.Param;

public interface InvitationCodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InvitationCode record);

    int insertSelective(InvitationCode record);

    InvitationCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InvitationCode record);

    int updateByPrimaryKey(InvitationCode record);

    InvitationCode getByParams(InvitationCode param);

    int updateByCode(@Param("code") String invitationCode);
}