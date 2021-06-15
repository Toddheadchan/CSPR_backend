package com.cnsmash.cspr.api.mapper;

import com.cnsmash.cspr.api.entity.Character;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
@Mapper
public interface CharacterMapper extends BaseMapper<Character> {

    public Character getCharacterDataByPlayerAndCharacter(int playerId, String character);

    public List<Character> getCharacterByPlayer(int playerId);

}
