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
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "player_id")
    private Long playerId;

    private String playerTag;

    private String avatar;

    private String nation;

    private Integer regionId;

    private String mainCharacter;

    private Integer mainCharacterColor;

    private String keyword;

    private String mostCharacter;

}
