package net.hydrogen2oxygen.biblestudycenter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Verse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long chapterId;
    private Integer number;
    private String description;
}