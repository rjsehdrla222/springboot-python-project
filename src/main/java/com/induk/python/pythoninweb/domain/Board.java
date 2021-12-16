package com.induk.python.pythoninweb.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Board {
    private long id;
    private String title;
    private String login_id;
    private String name;
    private String contents;
    private int view_cnt;
    private Timestamp create_date;
    private int category;

    public Board() {
    }

    public Board(String title, String login_id, String name, String contents, int category, int view_cnt) {
        this.title = title;
        this.login_id = login_id;
        this.name = name;
        this.contents = contents;
        this.category = category;
        this.view_cnt = view_cnt;
    }


}
