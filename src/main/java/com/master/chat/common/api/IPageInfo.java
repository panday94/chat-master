package com.master.chat.common.api;

import com.master.chat.common.utils.DozerUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 通用返回分页对象
 *
 * @author: Yang
 * @date: 2020/11/18
 * @version: 1.0.0

 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IPageInfo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页
     */
    private long current;

    /**
     * 页数
     */
    private long size;

    /**
     * 总数
     */
    private long total;

    /**
     * 结果集
     */
    private List<T> records;

    /**
     * 获取分页数据对象
     *
     * @param current 当前页
     * @param size    页数
     * @param total   总数量
     * @param records 记录
     * @param clz     类型
     * @return
     */
    public static <T, S> IPageInfo<T> getIPage(long current, long size, long total, List<S> records, Class<T> clz) {
        return new IPageInfo<T>(current, size, total, DozerUtil.convertor(records, clz));
    }

}
