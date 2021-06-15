package com.cnsmash.cspr.api.controller;


import com.cnsmash.cspr.api.dto.CharacterDto;
import com.cnsmash.cspr.framework.dto.KeyValueDto;
import com.cnsmash.cspr.api.entity.Character;
import com.cnsmash.cspr.api.mapper.CharacterMapper;
import com.cnsmash.cspr.api.service.ICharacterService;
import com.cnsmash.cspr.api.vo.CharacterDetail;
import com.cnsmash.cspr.framework.api.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Amaki
 * @since 2021-04-16
 */
@CrossOrigin
@RestController
@RequestMapping("/cspr/character")
public class CharacterController {

    @Autowired
    ICharacterService iCharacterService;

    @GetMapping("/rate/{playerId}")
    @ResponseBody
    public ApiResult<Map<String, Object> > getPlayerCharacterRate(@PathVariable("playerId") int playerId) {
        List<KeyValueDto<String, Double>> characterRate = iCharacterService.getPlayerCharacterRate(playerId);
        Map<String, Object> result = new HashMap<>();
        result.put("rate", characterRate);
        if (characterRate.size() == 0) {
            result.put("info", null);
        } else {
            result.put("info", iCharacterService.getCharacterDetail(playerId, characterRate.get(0).getKey()));
        }
        ApiResult<Map<String, Object> > ret = new ApiResult<>();
        return ret.ok(result);
    }

    @GetMapping("/detail/{playerId}/{character}")
    @ResponseBody
    public ApiResult<CharacterDetail> getCharacterDetail(@PathVariable("playerId") int playerId, @PathVariable("character") String character) {
        CharacterDetail characterDetail = iCharacterService.getCharacterDetail(playerId, character);
        ApiResult<CharacterDetail> result = new ApiResult<>();
        return result.ok(characterDetail);
    }

}

