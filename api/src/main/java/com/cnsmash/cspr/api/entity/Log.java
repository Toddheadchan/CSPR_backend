package com.cnsmash.cspr.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("cspr_log")
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "log_id")
    private String logId;

    private Long playerId;

    private Long tournamentId;

    private Integer oldCspr;

    private Integer newCspr;

    private Integer standing;

    public Log() {}

    public Log(long playerId, long tournamentId, int oldCspr, int newCspr, int standing) {
        this.playerId = playerId;
        this.tournamentId = tournamentId;
        this.oldCspr = oldCspr;
        this.newCspr = newCspr;
        this.standing = standing;
    }

}
