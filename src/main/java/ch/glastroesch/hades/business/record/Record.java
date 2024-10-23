package ch.glastroesch.hades.business.record;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

public class Record {

    private static final char RECORD_SEPARATOR = 0x1E;
    private static final char UNIT_SEPARATOR = 0x1F;

    private String name;
    private List<Column> columns = new ArrayList<>();
    private List<Currency> currencies;
    private List<Date> dates;

    public Record(String name) {
        this.name = name;
    }

    public void add(String name, String value) {

        Column column = new Column(name, value, ColumnType.STRING);
        this.columns.add(column);

    }

    public void add(String name, Date value) {

        String formattedDate = new SimpleDateFormat("yyyyMMdd").format(value);
        Column column = new Column(name, formattedDate, ColumnType.DATE);
        this.columns.add(column);

    }

    public String asString() {

        String values = "";
        for (Column column : this.columns) {
            values += column.getName() + UNIT_SEPARATOR + column.getValue() + RECORD_SEPARATOR;
        }

        return values;

    }

    public String getValue(String name) {

        for (Column column : this.columns) {
            if (column.getName().equals(name)) {
                return column.getValue();
            }
        }
        return null;
    }

    public String getCurrency(String name) {

        for (Currency currency : this.currencies) {
            if (currency.getCurrencyCode().equals(name)) {
                return currency.getCurrencyCode();
            }

        }

        return null;
    }

    public Date getDate(Date targetDate) {

        for (Date date : this.dates) {
            if (date.after(targetDate)) {
                return date;
            }
            return null;
        }

        throw new IllegalArgumentException("column " + name + " not found");

    }

    public String getName() {
        return name;
    }

    String getDate(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String createCountQuery() {

        String query = "select count(*) \"cnt\" from " + this.name + " ";

        int cnt = 0;
        for (Column column : this.columns) {
            if (cnt == 0) {
                query += "where ";
            } else {
                query += " and ";
            }

            if (column.getType() == ColumnType.DATE) {
                query += "to_char(" + column.getName().toLowerCase() + ", 'yyyymmdd') = '" + column.getValue() + "'";
            } else {
                query += column.getName().toLowerCase() + " = '" + column.getValue() + "'";
            }

            cnt++;
        }

        return query;

    }

}
