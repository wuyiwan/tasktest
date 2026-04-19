package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DatabaseMigrationConfig {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void migrateDatabase() {
        try {
            addColumnIfNotExists("task", "image_path", "VARCHAR(255)");
            addColumnIfNotExists("task", "description_detail", "TEXT");
        } catch (Exception e) {
            System.out.println("Database migration skipped or failed: " + e.getMessage());
        }
    }

    private void addColumnIfNotExists(String tableName, String columnName, String columnType) {
        try {
            jdbcTemplate.queryForObject(
                "SELECT " + columnName + " FROM " + tableName + " LIMIT 1",
                (rs, rowNum) -> rs.getString(1)
            );
            System.out.println("Column " + columnName + " already exists in " + tableName);
        } catch (Exception e) {
            try {
                String sql = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " " + columnType;
                jdbcTemplate.execute(sql);
                System.out.println("Successfully added column " + columnName + " to " + tableName);
            } catch (Exception ex) {
                System.out.println("Failed to add column " + columnName + ": " + ex.getMessage());
            }
        }
    }
}
