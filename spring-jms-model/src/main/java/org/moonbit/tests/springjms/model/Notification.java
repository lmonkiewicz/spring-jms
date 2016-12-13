package org.moonbit.tests.springjms.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Created by lmonkiewicz on 2016-12-13.
 */
@Data
public class Notification {
    private Date date;
    private String id;
    private String title;

    public Notification() {
    }

    @Builder
    public Notification(Date date, String id, String title) {
        this.date = date;
        this.id = id;
        this.title = title;
    }
}
