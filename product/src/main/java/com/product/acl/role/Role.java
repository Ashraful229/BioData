package com.product.acl.role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "ACL_ROLE")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ")
    @SequenceGenerator(name = "SEQ", sequenceName = "ROLE_SEQ")
    @Column(name = "ID")
    private Long id;
    private String authority;
    private String description;

    // System log fields
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "CREATION_DATETIME")
    private Date creationDateTime;
    @Column(name = "CREATION_USER")
    private String creationUser;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "LAST_UPDATE_DATETIME")
    private Date lastUpdateDateTime;
    @Column(name = "LAST_UPDATE_USER")
    private String lastUpdateUser;
}
