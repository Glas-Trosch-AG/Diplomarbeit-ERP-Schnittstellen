package ch.glastroesch.hades.business.record;

public class Column {

    private String name;
    private String value;
    private ColumnType type;

    public Column(String name, String value, ColumnType type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public ColumnType getType() {
        return type;
    }

}
