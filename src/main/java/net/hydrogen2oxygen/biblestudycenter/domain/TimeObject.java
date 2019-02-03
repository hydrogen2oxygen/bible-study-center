package net.hydrogen2oxygen.biblestudycenter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeObject {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long timelineId;
    private String name;
    private Calendar start;
    private Calendar end;
}
