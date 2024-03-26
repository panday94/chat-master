package com.master.chat.api.moonshot.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 模型
 *
 * @author: Yang
 * @date: 2024/3/25
 * @version: 1.2.0
 * Copyright Ⓒ 2023 Master Computer Corporation Limited All rights reserved.
 */
@Data
public class Model {

    private String id;
    private String object;

    @SerializedName("owner_by")
    private String ownedBy;
    private String root;
    private String parent;

    public Model(String id, String object, String ownedBy, String root, String parent) {
        this.id = id;
        this.object = object;
        this.ownedBy = ownedBy;
        this.root = root;
        this.parent = parent;
    }

}
