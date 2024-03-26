package com.ifejeremiah.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public abstract class BaseObject implements Serializable {
    private String lastUpdatedBy;
    private Date lastUpdatedOn;
    private String createdBy;
    private Date createdOn;
}
