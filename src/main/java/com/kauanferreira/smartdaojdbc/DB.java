package com.kauanferreira.smartdaojdbc;

import com.kauanferreira.smartdaojdbc.exception.DbException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Classe utilitária para gerenciar conexões com o banco de dados PostgreSQL.
 * Utiliza o padrão Singleton para manter uma única conexão ativa.
 * As configurações são carregadas do arquivo db.properties.
 *
 * @author Kauan
 * @version 1.0
 * @since 2026
 */
public class DB {

    /** Conexão única (Singleton) com o banco de dados. */
    private static Connection connection = null;

    /**
     * Obtém a conexão com o banco de dados.
     * Se a conexão ainda não existir, cria uma nova usando as propriedades
     * do arquivo db.properties. Caso já exista, retorna a mesma conexão.
     *
     * @return Connection objeto de conexão ativa com o banco de dados
     * @throws DbException se ocorrer erro ao estabelecer a conexão
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                connection = DriverManager.getConnection(url, props);
            } catch (Exception e) {
                throw new DbException(e.getMessage());
            }
        }
        return connection;
    }

    /**
     * Carrega as propriedades de conexão do arquivo db.properties.
     * O arquivo deve estar localizado em src/main/resources/ e conter:
     * <ul>
     *     <li>user - usuário do banco de dados</li>
     *     <li>password - senha do banco de dados</li>
     *     <li>dburl - URL de conexão JDBC (ex: jdbc:postgresql://localhost:5432/livraria)</li>
     * </ul>
     *
     * @return Properties objeto contendo as configurações de conexão
     * @throws DbException se o arquivo db.properties não for encontrado ou não puder ser lido
     */
    private static Properties loadProperties() {
        try (InputStream fs = DB.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    /**
     * Fecha a conexão ativa com o banco de dados.
     * Após o fechamento, a conexão é definida como null para permitir
     * uma nova conexão futura caso necessário.
     *
     * @throws DbException se ocorrer erro ao fechar a conexão
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (Exception e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    /**
     * Fecha um objeto Statement de forma segura.
     * Verifica se o Statement não é nulo antes de tentar fechar.
     *
     * @param stmt o Statement a ser fechado, pode ser null
     * @throws DbException se ocorrer erro ao fechar o Statement
     */
    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    /**
     * Fecha um objeto ResultSet de forma segura.
     * Verifica se o ResultSet não é nulo antes de tentar fechar.
     *
     * @param rs o ResultSet a ser fechado, pode ser null
     * @throws DbException se ocorrer erro ao fechar o ResultSet
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}