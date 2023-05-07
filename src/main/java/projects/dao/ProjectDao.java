package projects.dao;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import projects.entity.Project;
import projects.exception.DbException;
import provided.util.DaoBase;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProjectDao extends DaoBase {
    private static final String CATEGORY_TABLE = "category";
    private static final String MATERIAL_TABLE = "material";
    private static final String PROJECT_TABLE = "project";
    private static final String PROJECT_CATEGORY_TABLE = "project_category";
    private static final String STEP_TABLE = "step";
    public Project insertProject(Project project) {
        // SQL query to insert a new project into the project table
       // @formatter:off
        String sql = ""
            + "INSERT INTO " + PROJECT_TABLE + " "
            + "(project_name, estimated_hours, actual_hours, difficulty, notes) "
            + "VALUES "
            + "(?, ?, ?, ?, ?)";
        //formatter:on

        try(Connection conn = DbConnection.getConnection()) {
            // Begin transaction
            startTransaction(conn);

            try(PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Set the parameters for the prepared statement
                setParameter(stmt, 1, project.getProjectName(), String.class);
                setParameter(stmt, 2, project.getEstimatedHours(), BigDecimal.class);
                setParameter(stmt, 3, project.getActualHours(), BigDecimal.class);
                setParameter(stmt, 4, project.getDifficulty(), Integer.class);
                setParameter(stmt, 5, project.getNotes(), String.class);
                // Execute the SQL statement
                stmt.executeUpdate();

                // Get the ID of the last inserted record
                Integer projectId = getLastInsertId(conn, PROJECT_TABLE);

                // Commit the transaction
                commitmentTransaction(conn);

                // Set the project ID for the project object and return it
                project.setProjectId(projectId);
                return project;
            }
            catch (Exception e) {
                // Roll back the transaction if there was an error
                rollbackTransaction(conn);
                throw new DbException(e);
            }
        }
        catch (SQLException e) {
            throw new DbException(e);
        }
        // Method to commit a transaction
    }private void commitmentTransaction(Connection conn) {
        // TODO: Implement this method
    }
}
