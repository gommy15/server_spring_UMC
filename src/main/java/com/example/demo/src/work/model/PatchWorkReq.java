package com.example.demo.src.work.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchWorkReq {
    private boolean complete;
    private String workName;
}
