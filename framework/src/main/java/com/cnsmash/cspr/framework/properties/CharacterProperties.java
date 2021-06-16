package com.cnsmash.cspr.framework.properties;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CharacterProperties {

    private static Map<String, String> characterIdMap;

    public static Map<String, String> getCharacterIdMap () {
        if (characterIdMap == null) {
            try {
                ClassPathResource classPathResource = new ClassPathResource("data/CharacterId.json");
                InputStream inputStream = classPathResource.getInputStream();
                byte b[] = new byte[(int) classPathResource.contentLength()];
                inputStream.read(b);
                inputStream.close();
                String characterData = new String(b);
                Gson gson = new Gson();
                Map<Object, Object> characterDataMap = new HashMap<>();
                characterDataMap = gson.fromJson(characterData, characterDataMap.getClass());
                List<Map<Object, String>> characterList = (List) characterDataMap.get("characterId");
                characterIdMap = new HashMap<>();
                for (Map<Object, String> mp: characterList) {
                    characterIdMap.put(mp.get("id"), mp.get("name"));
                }
            } catch (Exception e) {
                log.error(e.toString());
            }
        }
        return characterIdMap;
    }

}
