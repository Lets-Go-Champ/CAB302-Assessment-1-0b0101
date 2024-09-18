package com.example.cab302assessment10b0101.model;

import java.util.List;

public interface CollectionDAOInterface {
    void insert(Collection collection);
    List<Collection> getAll();
}
