package com.example.chatchannel.Repository;
import com.example.chatchannel.Model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("channelRepository")
public interface JpaChannelRepository extends JpaRepository<Channel, Long> {
}