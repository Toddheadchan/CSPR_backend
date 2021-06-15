package com.cnsmash.cspr.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Participate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "participate_id")
    private String participateId;

    private Long playerId;

    private Long tournamentId;

    private Integer standing;

    @TableField(value = "`character`")
    private String character;

    private Integer isDisqualified;

}
