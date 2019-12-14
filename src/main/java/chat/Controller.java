package chat;

import chat.model.ChatRoom;
import chat.model.Message;

import javax.naming.ldap.Control;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador de la aplicación. Por favor, revise detenidamente la clase y complete las partes omitidas
 * atendiendo a los comentarios indicados mediante @TODO
 */
public class Controller {

  // @TODO: Sustituya xxxx por los parámetros de su conexión
    private final String DB_SERVER = "bhcwv8kdmdkrydy512sa-mysql.services.clever-cloud.com";
    private final int DB_PORT = 3306;
    private final String DB_NAME = "bhcwv8kdmdkrydy512sa";
    private final String DB_USER = "uw64dxldgzrc0fra";
    private final String DB_PASS = "xviv5csnZmexnxpON4LL";

    private Connection conn;


 /**
    private final String DB_SERVER = "localhost";
    private final int DB_PORT = 3306;
    private final String DB_NAME = "chat";
    private final String DB_USER = "root";
    private final String DB_PASS = "xve$8520";

    private Connection conn;
*/
    /**
     * Conexion para BD en docker
     */

    /**
     * // @TODO: Sustituya xxxx por los parámetros de su conexión
     *     private final String DB_SERVER = "bhcwv8kdmdkrydy512sa-mysql.services.clever-cloud.com";
     *     private final int DB_PORT = 3306;
     *     private final String DB_NAME = "bhcwv8kdmdkrydy512sa";
     *     private final String DB_USER = "uw64dxldgzrc0fra";
     *     private final String DB_PASS = "xviv5csnZmexnxpON4LL";
     *
     *     private Connection conn;
     */

    /**
     * Crea un nuevo controlador
     */

    public Controller () {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            String url = "jdbc:mysql://" + DB_SERVER + ":" + DB_PORT + "/" + DB_NAME;
            this.conn = DriverManager.getConnection(url, DB_USER, DB_PASS);

        } catch (Exception e) {
            System.err.println("Ha ocurrido un error al conectar con la base de datos");
            e.printStackTrace();
        }
    }



    /**
     * Crea un nuevo usuario en la aplicación
     * @param username nombre de usuario
     * @return id del nuevo usuario creado
     * @throws SQLException
     */
    public long createUser(String username) throws SQLException {

        // @TODO: complete este método para que cree un nuevo usuario en la base de datos
        //
        // Al haberse definido el atributo 'id' de la tabla 'users' como AUTO_INCREMENT, este será generado
        // automáticamente por la base de datos. Para recuperar el 'id' generado para un usuario deberá:
        //
        //  (1) Instanciar un nuevo PreparedStatement dando como valor al segundo parámetro Statement.RETURN_GENERATED_KEYS
        //  (2) Recuperar el id generado mediante una llamada al método privado this.getAutogeneratedId(stmt)

        PreparedStatement stmt = conn.prepareStatement("INSERT INTO users(username) VALUES (?)", Statement.RETURN_GENERATED_KEYS);

        stmt.setString(Statement.RETURN_GENERATED_KEYS, username);

        long id = this.getAutogeneratedId(stmt);

        stmt.executeUpdate();

        stmt.close();

        return id;
    }

    /**
     * Crea una nueva sala de chat
     * @param userId id del usuario que crea la sala de chat
     * @param chatRoomName nombre de la sala de chat
     * @return id de la nueva sala de chat creada
     * @throws SQLException
     */
    public long createChatRoom (long userId, String chatRoomName) throws SQLException {

        // @TODO: complete este método para que cree una nueva sala de chat en la base de datos
        //
        // Al haberse definido el atributo 'id' de la tabla 'chatrooms' como AUTO_INCREMENT, este será generado
        // automáticamente por la base de datos. Para recuperar el 'id' generado para una sala de chat deberá:
        //
        //  (1) Instanciar un nuevo PreparedStatement dando como valor al segundo parámetro Statement.RETURN_GENERATED_KEYS
        //  (2) Recuperar el id generado mediante una llamada al método privado this.getAutogeneratedId(stmt)

        PreparedStatement stmt = conn.prepareStatement("INSERT INTO chatrooms(name, createdBy) VALUES (?,?)");


        long id = this.getAutogeneratedId(stmt);

        stmt.setString(1,chatRoomName);

        stmt.executeUpdate();

        stmt.close();

        return id;

    }

    /**
     * Crea un nuevo mensaje en una sala de chat
     * @param userId id del usuario que crea el mensaje
     * @param chatRoomId id de la sala de chat en la que se crea el mensaje
     * @param text contenido del mensaje
     * @throws SQLException
     */
    public void sendMessage (long userId, long chatRoomId, String text) throws SQLException {

        // @TODO: complete este método para que cree un nuevo mensaje en la base de datos
        //
        // Tenga en cuenta que las columnas 'id' y 'ts' generan el valor de sus atributos de forma automática. No
        // es necesario definir ningún valor para las mismas.
        //
        // El 'id' del mensaje no es necesario recuperarlo.

    }

    /**
     * Recupera los mensajes de una sala de chat
     * @param chatRoomId id de la sala de chat
     * @return Lista de mensajes
     * @throws SQLException
     */
    public List<Message> getMessages (long chatRoomId) throws SQLException {

        // @TODO: complete este método para que consulte los mensajes de una sala de chat y los devuelva como una lista de objetos Message
        //
        // Tenga en cuenta que la consulta a la base de datos le devolverá un ResultSet que deberá transformar
        // en una lista de instancias de objetos Message. Consulte la clase chat.model.Message para ver como crear
        // instancias de la misma

        return getMessages(0);  //es un return de prueba @fernando

    }

    /**
     * Recupera un listado con todas las salas de chat
     * @return listado con las salas de chat
     * @throws SQLException
     */
    public List<ChatRoom> getChatRooms () throws SQLException {

        // @TODO: complete este método para que consulte todas las salas de chat y las devuelva como una lista de objetos ChatRoom
        //
        // Tenga en cuenta que la consulta a la base de datos le devolverá un ResultSet que deberá transformar
        // en una lista de instancias de objetos ChatRoom. Consulte la clase chat.model.ChatRoom para ver como crear
        // instancias de la misma

        return getChatRooms();

    }

    /**
     * Devuelve el id generado por un Statement
     * @param stmt el Statement
     * @return el id
     * @throws SQLException
     */
    private long getAutogeneratedId (Statement stmt) throws SQLException {
        ResultSet keys = stmt.getGeneratedKeys();
        keys.next();
        return keys.getLong(1);
    }
}
