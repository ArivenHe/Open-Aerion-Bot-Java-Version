package com.ariven.controller;

import com.ariven.service.IOnlineService;
import com.ariven.service.impl.OnlineServiceImpl;
import com.ariven.vo.OnlineVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OnlineController {
    private final IOnlineService onlineService = new OnlineServiceImpl();

    public String getOnlineInfo() {
        try {
            OnlineVO online = onlineService.getOnlineStats();
            if (online != null) {
                String atcFieldsStr = String.join("\n", online.getAtcFields());
                return String.format("\n模拟机个数: %d\n管制员人数: %d\n席位:\n%s",
                        online.getPilotCount(), online.getAtcCount(), atcFieldsStr);
            } else {
                return "获取在线数据失败。";
            }
        } catch (Exception e) {
            log.error("Error fetching online data", e);
            return "Error fetching online data. Please try again later. Exception: " + e.getMessage();
        }
    }
}
