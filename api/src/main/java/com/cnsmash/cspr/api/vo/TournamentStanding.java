package com.cnsmash.cspr.api.vo;

import com.cnsmash.cspr.api.entity.Participate;
import com.cnsmash.cspr.api.entity.Tournament;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 比赛排名数据
 */

@Data
public class TournamentStanding {

    // 玩家ID
    private long playerId;

    // 玩家TAG
    private String playerTag;

    // 玩家头像
    private String avatar;

    // 排名
    private int standing;

    // 胜局数
    private int setWin;

    // 败局数
    private int setLose;

    // 使用角色
    private String character;

    // 是否DQ
    private Integer isDisqualified;

    // 败北对手
    private List<PlayerLite> losing;

    public TournamentStanding() {
        this.losing = new LinkedList<>();
    }

    public TournamentStanding(Participate participate, PlayerLite player) {
        this.playerId = player.getPlayerId();
        this.playerTag = player.getPlayerTag();
        this.avatar = player.getAvatar();
        this.standing = participate.getStanding();
        this.character = participate.getCharacter();
        this.isDisqualified = participate.getIsDisqualified();
        this.losing = new LinkedList<>();
    }

    public PlayerLite playerLite() {
        PlayerLite player = new PlayerLite();
        player.setPlayerId(playerId);
        player.setPlayerTag(playerTag);
        player.setAvatar(avatar);
        return player;
    }

    public void addLosingPlayer(PlayerLite player) {
        this.setLose += 1;
        this.losing.add(player);
    }

    public void addWinSet() {this.setWin += 1;}

}
