package com.cnsmash.cspr.api.service;

import com.cnsmash.cspr.api.dto.CharacterDto;
import com.cnsmash.cspr.framework.dto.KeyValueDto;
import com.cnsmash.cspr.api.entity.Character;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cnsmash.cspr.api.vo.CharacterDetail;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
public interface ICharacterService extends IService<Character> {

    public CharacterDto getCharacterData(int playerId, String character);

    public List<KeyValueDto<String, Double> > getPlayerCharacterRate(int playerId);

    public CharacterDetail getCharacterDetail(int playerId, String character);
}
