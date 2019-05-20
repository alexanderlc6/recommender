package com.sp.cf.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by alexlu
 * 2018/1/29.
 */
public class MahoutBaseItemDto {

    @NotNull(message = "userId不能为空")
    private Long userId;
    @NotNull(message = "推荐数量不能为空")
    private Integer amount;
    @NotNull(message = "内容Id不能为空")
    private Long itemId;

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

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}
