package com.master.chat.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 下拉选择内容
 *
 * @author: Yang
 * @date: 2023/8/30
 * @version: 1.2.0

 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 标签
     */
    private String label;

    /**
     * 字典值
     */
    private Object value;

    /**
     * 子集
     */
    private List<SelectDTO> children;

}
