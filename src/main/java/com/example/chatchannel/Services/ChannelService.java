package com.example.chatchannel.Services;
import com.example.chatchannel.Model.Channel;
import com.example.chatchannel.Repository.JpaChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChannelService {
    @Autowired
    private JpaChannelRepository channelRepository;

    public List<Channel> getAll() {
        return channelRepository.findAll();
    }

    public Channel get(long id) {
        return channelRepository.getById(id);
    }

    public Channel save(Channel channel) {
        return channelRepository.save(channel);
    }

    public void delete(long channelId) {
        channelRepository.deleteById(channelId);
    }
}
