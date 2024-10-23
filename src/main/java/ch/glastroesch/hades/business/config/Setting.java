package ch.glastroesch.hades.business.config;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Setting")
public class Setting {

    @Id
    @Column(name = "settingKey")
    private String settingKey;

    @Column(name = "settingValue")
    private String settingValue;

    public Setting() {
        // for jpa
    }

    public Setting(String key, String value) {
        this.settingKey = key;
        this.settingValue = value;
    }

    public String getSettingKey() {
        return settingKey;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }

}
