package com.cnsmash.cspr.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "game_id")
    private Long gameId;

    private Long setId;

    private Integer gameIndex;

    private Integer player1Stock;

    private String player1Character;

    private Integer player2Stock;

    private String player2Character;

    private String stage;

    private Long winnerId;
}
