package com.dxc.application.feature.usermaster.service;

import com.dxc.application.feature.usermaster.data.database.UserMasterMapper;
import com.dxc.application.feature.usermaster.data.database.model.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserMasterService {

    private UserMasterMapper userMasterMapper;

    @Autowired
    UserMasterService(UserMasterMapper userMasterMapper) {
        this.userMasterMapper = userMasterMapper;
    }

    @Transactional(value = "mybastistx", readOnly = true)
    public List<User> getUser(User criteria) {
        return userMasterMapper.findUser(criteria);
    }

    @Transactional(value = "mybastistx", rollbackFor = Exception.class)
    @SneakyThrows
    public int insertUser(User user) {
        return userMasterMapper.insertUser(user);
    }

    @Transactional(value = "mybastistx", rollbackFor = Exception.class)
    @SneakyThrows
    public int updateUser(User user) {
        return userMasterMapper.updateUser(user);
    }

    @Transactional(value = "mybastistx", rollbackFor = Exception.class)
    public int deleteUser(User[] users) {
        int deleteRowCount = 0;
        for (User user : users) {
            deleteRowCount += userMasterMapper.deleteUserByKey(user);
        }
        return deleteRowCount;
    }
}
