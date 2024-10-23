package ch.glastroesch.hades.business.record;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordRepository {

    private final static Logger LOGGER = Logger.getLogger(RecordRepository.class.getName());

    @Autowired
    IfsConnectionService connectionService;

    public void save(Record record) throws SQLException {

        String parameters = record.asString();

       
            CallableStatement statement = connectionService.getConnection().prepareCall("{call ifsapp." + record.getName() + "_api.new__(?, ?, ?, ?, ?)}");

            statement.setNull(1, Types.VARCHAR);
            statement.setNull(2, Types.VARCHAR);
            statement.setNull(3, Types.VARCHAR);
            statement.setString(4, parameters);
            statement.setString(5, "DO");

            statement.executeUpdate();
            statement.close();
  

    }

    public boolean exists(Record record) throws SQLException {

        Statement statement = connectionService.getConnection().createStatement();
        ResultSet row = statement.executeQuery(record.createCountQuery());
        row.next();

        return row.getInt("cnt") >= 1;

    }

}
