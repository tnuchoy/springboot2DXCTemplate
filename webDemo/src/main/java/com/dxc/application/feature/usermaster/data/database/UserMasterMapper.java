package com.dxc.application.feature.usermaster.data.database;

import com.dxc.application.feature.usermaster.data.database.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMasterMapper {

    public User findUserByKey(@Param("citizenId") String citizenId);

    public List<User> findUser(User user);

    public int insertUser(User criteria);

    public int updateUser(User criteria);

    public int deleteUserByKey(User criteria);
}
