package com.group3.torikago.Torikago.Shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "persistent_logins")
public class PersistentLogins {
        @Id
        @Column(name = "series", length = 64)
        private String series;
        @Column(name = "username", length = 64)
        private String userName;
        @Column(name = "token", length = 64)
        private String token;
        @Column(name = "last_used", length = 64)
        private Timestamp lastUsed;
}
