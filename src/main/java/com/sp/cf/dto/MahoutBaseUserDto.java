package com.sp.cf.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by alexlu
 * 2018/1/29.
 */
public class MahoutBaseUserDto {

    @NotNull(message = "userId不能为空")
    private Long userId;
    @NotNull(message = "推荐数量不能为空")
    private Integer amount;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
