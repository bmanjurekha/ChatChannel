package com.example.chatchannel.Controllers;
import com.example.chatchannel.Model.Channel;
import com.example.chatchannel.Model.ChannelStateDetails;
import com.example.chatchannel.Services.ChannelService;
import com.example.chatchannel.WS.ChannelSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class ChannelController {
    @Autowired
    private ChannelSocketHandler channelSocketHandler;
    @Autowired
    private ChannelService channelService;

    @GetMapping("channel/{channelId}")
    public ResponseEntity<Channel> getChannelById(@PathVariable long channelId) {
        return ResponseEntity.ok(channelService.get(channelId));
    }

    @GetMapping("channel")
    public ResponseEntity<List<Channel>> getAllChannels() {
        return ResponseEntity.ok(channelService.getAll());
    }

    @PostMapping("channel")
    @PatchMapping("channel")
    @PutMapping("channel")
    public ResponseEntity<List<Channel>> addChannel(@RequestBody Channel channel) {
        channelService.save(channel);
        channelSocketHandler.broadcast("new-channel", channel.getTitle() + " was created");
        return getAllChannels();
    }

    @DeleteMapping("channel")
    public ResponseEntity<List<Channel>> deleteChannel(@RequestBody Channel channel) {
        channelService.delete(channel.getId());
        return getAllChannels();
    }

    @DeleteMapping("channel/{channelId}")
    public ResponseEntity<List<Channel>> deleteChannel(@PathVariable long channelid) {
        channelService.delete(channelid);
        return getAllChannels();
    }

    @PatchMapping("channel/online/{state}/{channelId}")
    public void setOnlineState(@PathVariable String state, @PathVariable long channelId) {
        Channel newChannel = channelService.get(channelId);
        Channel oldChannel = newChannel.clone();

        switch(state) {
            case "online": newChannel.setOnline(true); break;
            case "offline": newChannel.setOnline(false); break;
            default: throw new IllegalStateException(state + " was illdefined");
        }


        channelService.save(newChannel);

        // broadcast channel changes
        if(newChannel.isOnline()) {
            channelSocketHandler.broadcast("online", oldChannel, newChannel);
        } else {
            channelSocketHandler.broadcast("offline", oldChannel, newChannel);
        }
    }
}
