import java.sql.Connection;
import java.sql.SQLException;

import com.modelo.jdbc.ConnectionFactory;

public class TestaConexao {

	public static void main(String[] args) throws SQLException{
		Connection conn = ConnectionFactory.getConnection();
		
		if(conn != null) {
			ConnectionFactory.closeConnection(conn);
			System.out.println("Conexao fechada!");
		} else {
			System.out.println("Nenhuma conexao para ser fechada");
		}
	}

}
