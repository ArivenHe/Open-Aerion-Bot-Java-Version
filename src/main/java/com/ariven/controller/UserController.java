package com.ariven.controller;

import com.ariven.service.IUserService;
import com.ariven.service.impl.UserServiceImpl;
import com.ariven.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserController {
    private final IUserService userService = new UserServiceImpl();

    public String getUserInfo(String callsign) {
        try {
            UserVO user = userService.getUserData(callsign);
            if (user != null) {
                return String.format("\n权限查询结果\n编号: %s\nQQ: %s\n连线时长: %s\n等级: %s\n",
                        user.getCid(), user.getQq(), user.getOnlineTime(), user.getRating());
            } else {
                return "该账号尚未注册，请前往空管模拟机系统注册。";
            }
        } catch (Exception e) {
            return "Error fetching user data. Please try again later. Exception: " + e.getMessage();
        }
    }
}
