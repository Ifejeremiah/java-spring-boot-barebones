package com.ifejeremiah.service;

import com.ifejeremiah.dao.BaseDao;
import com.ifejeremiah.exception.NotFoundException;
import com.ifejeremiah.model.BaseObject;
import com.ifejeremiah.model.Page;

import java.util.List;

public class BaseService<T extends BaseObject> {
    protected BaseDao<T> dao;

    public long create(T model) {
        return dao.create(model);
    }

    public void update(T model) {
        dao.update(model);
    }

    public Page<T> findAll(Integer pageNum, Integer pageSize) {
        return dao.findAll(pageNum, pageSize);
    }

    public List<T> findAll() {
        return dao.findAll();
    }

    public T findById(Integer id) throws NotFoundException {
        T data = dao.find(id);
        if (data == null) throw new NotFoundException("Entity with this ID could not be found");
        return data;
    }
}
