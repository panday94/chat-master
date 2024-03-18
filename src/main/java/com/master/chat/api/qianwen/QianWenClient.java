package com.master.chat.api.qianwen;

import lombok.Data;

/**
 * 通义千问client
 *
 * @author: Yang
 * @date: 2023/12/4
 * @version: 1.0.0
 * Copyright Ⓒ 2023 MasterComputer Corporation Limited All rights reserved.
 */
@Data
public class QianWenClient {

    private String appKey;

    public QianWenClient() {
    }

    public QianWenClient(String appKey) {
        this.appKey = appKey;
    }

}
