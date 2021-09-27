package com.jtexplorer.entity;

import lombok.Data;

@Data
public class JsonRootBean {

    private int status;
    private String message;
    private TXResult result;

}
