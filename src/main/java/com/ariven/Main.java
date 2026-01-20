package com.ariven;

import com.ariven.controller.SwitchController;
import com.ariven.pojo.Auth;
import com.ariven.utils.ConfigUtil;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.Intents;
import io.github.kloping.qqbot.api.v2.GroupMessageEvent;
import io.github.kloping.qqbot.impl.ListenerHost;
import io.github.mivek.exception.ParseException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor
public class Main {
    static SwitchController switchController = new SwitchController();

    public static void main(String[] args) {

        log.info("Application starting...");

        Auth auth = ConfigUtil.getAuth();
        log.info("Loaded Auth Config: AppId={}, AppToken=******, AppSecret=******", auth.getAppId());

        Starter starter = new Starter(auth.getAppId(), auth.getAppToken(), auth.getAppSecret());
        starter.getConfig().setCode(Intents.PUBLIC_INTENTS.and(Intents.GROUP_INTENTS));
        starter.run();
        log.info("[AerionBot-JavaVersion] 启动成功!,目前为1.0版本仅支持文字发送！");

        starter.registerListenerHost(new ListenerHost() {
            @EventReceiver
            public void onMessage(GroupMessageEvent event) throws ParseException {
                String data = switchController.choice(String.valueOf(event));
                event.send(data);
            }
        });

        log.info("Application finished.");
    }
}