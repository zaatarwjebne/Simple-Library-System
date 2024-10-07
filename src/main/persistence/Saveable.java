package main.persistence;

import java.sql.PreparedStatement;
import java.sql.SQLException;

// Interface for objects that can be saved to a database
public interface Saveable {

    /**
     * Writes the saveable object to the provided PreparedStatement.
     * <p>
     * MODIFIES: pstmt (the PreparedStatement to be populated with data)
     * EFFECTS: Saves the state of the implementing object to the database via the PreparedStatement.
     *          Throws SQLException if an SQL error occurs during the save operation.
     *
     * @param pstmt the PreparedStatement used to save the object data
     * @throws SQLException if an SQL error occurs during the execution
     */
    void save(PreparedStatement pstmt) throws SQLException;
}
