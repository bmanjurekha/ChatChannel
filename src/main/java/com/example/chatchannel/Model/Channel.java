package com.example.chatchannel.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Entity
@NoArgsConstructor
@Setter @Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Channel implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    private boolean isOnline;

    @Override
    public Channel clone() {
        try {
            return (Channel) super.clone();
        } catch (CloneNotSupportedException e) {
            Channel channel = new Channel();

            channel.setId(this.getId());
            channel.setTitle(this.getTitle());
            channel.setOnline(this.isOnline());

            return channel;
        }
    }
}