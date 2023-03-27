package com.askus.askus.domain.image.domain;

import com.askus.askus.domain.common.BaseEntity;
import com.askus.askus.domain.users.domain.Users;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "profile_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @JoinColumn(name = "user_id")
    @OneToOne
    private Users users;

    @Column
    private String url;

    public ProfileImage(Users users, String url) {
        this.users = users;
        this.url = url;
    }
}
