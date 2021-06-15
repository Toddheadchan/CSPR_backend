package com.cnsmash.cspr.api.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author Amaki
 * @since 2021-04-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class League implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "league_id")
    private Long leagueId;

    private Long ownerId;

    private String name;

    private Integer regionId;

    private String logo;

    private Integer state;

    private String slug;

    private LocalDateTime lastUpdate;


}
