package com.induk.python.pythoninweb.domain;

import lombok.Data;

@Data
public class Member {
    Long id;
    String name;
    String login_id;
    String pw;
}
