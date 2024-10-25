package com.perfree.old;

import com.perfree.plugin.BasePluginEvent;
import com.perfree.plugin.commons.PluginUtils;
import com.perfree.system.api.menu.MenuApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class PluginEventService implements BasePluginEvent {

    @Resource
    private MenuApi menuApi;

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onUpdate() {
    }

    @Override
    public void onInstall() {
    }

    @Override
    public void onUnInstall() {
        String pluginId = PluginUtils.getPluginConfig(this.getClass()).getPlugin().getId();
        // 卸载时删除插件菜单
        menuApi.deleteMenuByPluginId(pluginId);
    }
}
