package com.induk.python.pythoninweb.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment {
    Long id;
    String board_id;

    String login_id;
    String name;
    String comment;
}
