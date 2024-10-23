package ch.glastroesch.hades.business.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingService {

    @Autowired
    SettingRepository settingRepository;

    public void set(String key, String value) {

        Setting setting;
        if (settingRepository.existsById(key)) {
            setting = settingRepository.findById(key).get();
            setting.setSettingValue(value);
        } else {
            setting = new Setting(key, value);
            settingRepository.save(setting);
        }

    }

    public String get(String key) {

        if (!settingRepository.existsById(key)) {
            throw new IllegalArgumentException("setting " + key + " not found");
        }

        return settingRepository.findById(key).get().getSettingValue();

    }

    public int asInteger(String key, int defaultValue) {

        if (!settingRepository.existsById(key)) {
            return defaultValue;
        }

        return Integer.valueOf(settingRepository.findById(key).get().getSettingValue());

    }

    public boolean asBoolean(String key, boolean defaultValue) {

        if (!settingRepository.existsById(key)) {
            return defaultValue;
        }

        return Boolean.valueOf(settingRepository.findById(key).get().getSettingValue());

    }

    public String get(String key, String defaultValue) {

        if (!settingRepository.existsById(key)) {
            return defaultValue;
        }

        return settingRepository.findById(key).get().getSettingValue();

    }

}
