package com.cnsmash.cspr.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName(value = "`set`")
public class Set implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "set_id")
    private Long setId;

    private Long tournamentId;

    private Integer roundNumber;

    private String roundText;

    private Long player1Id;

    private Integer player1Score;

    private Long player2Id;

    private Integer player2Score;

    private Long winnerId;

    private String displayScore;

    private Integer dq;


}
