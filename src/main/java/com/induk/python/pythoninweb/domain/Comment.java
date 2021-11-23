package com.induk.python.pythoninweb.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {
    private long id;
    private String title;
    private String login_id;
    private String name;
    private String comment;
    private int view_cnt;
    private Timestamp create_date;
}
