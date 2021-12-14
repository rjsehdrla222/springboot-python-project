package com.induk.python.pythoninweb.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface ApiRepository {
    String apiSelectValue();
    void apiValueUpdate(String value);
}
