package gov.dane.indices.sqlite.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import gov.dane.indices.sqlite.dao.CantidadRecolectadaDao;
import gov.dane.indices.sqlite.dao.CategoriaElementoDao;
import gov.dane.indices.sqlite.dao.CiudadDao;
import gov.dane.indices.sqlite.dao.ClaseDao;
import gov.dane.indices.sqlite.dao.DetalleEspecificacionDao;
import gov.dane.indices.sqlite.dao.DireccionEstandarDao;
import gov.dane.indices.sqlite.dao.ElementoDao;
import gov.dane.indices.sqlite.dao.ElementoEspecificacionDao;
import gov.dane.indices.sqlite.dao.EspecificacionCategoriaDao;
import gov.dane.indices.sqlite.dao.EstadoFuenteInfoDao;
import gov.dane.indices.sqlite.dao.EstratoDao;
import gov.dane.indices.sqlite.dao.ListaEspecificacionesDao;
import gov.dane.indices.sqlite.dao.NovedadesProductoDao;
import gov.dane.indices.sqlite.dao.ObservacionDao;
import gov.dane.indices.sqlite.dao.PuntoVentaDao;


public class SqliteManager {

	DatabaseConnecter databaseConnecter;
	public SqliteManager(DatabaseConnecter databaseConnecter) {
		this.databaseConnecter = databaseConnecter;
	}

	public void createTablas(){
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = databaseConnecter.createConnection();
			statement = connection.createStatement();
			
			statement.execute(CiudadDao.dropTable(true));
			statement.executeUpdate(CiudadDao.createTable(true));
			
			
			statement.execute(CiudadDao.dropTable(true));
			statement.executeUpdate(CiudadDao.createTable(true));
			
			
			statement.execute(ClaseDao.dropTable(true));
			statement.executeUpdate(ClaseDao.createTable(true));
			
			
			statement.execute(ElementoDao.dropTable(true));
			statement.executeUpdate(ElementoDao.createTable(true));
			
			statement.execute(ElementoEspecificacionDao.dropTable(true));
			statement.executeUpdate(ElementoEspecificacionDao.createTable(true));
			
			statement.execute(EstratoDao.dropTable(true));
			statement.executeUpdate(EstratoDao.createTable(true));
			
			statement.execute(ObservacionDao.dropTable(true));
			statement.executeUpdate(ObservacionDao.createTable(true));
			
			statement.execute(PuntoVentaDao.dropTable(true));
			statement.executeUpdate(PuntoVentaDao.createTable(true));
			
			statement.execute(DetalleEspecificacionDao.dropTable(true));
			statement.executeUpdate(DetalleEspecificacionDao.createTable(true));
			
			
			statement.execute(DireccionEstandarDao.dropTable(true));
			statement.executeUpdate(DireccionEstandarDao.createTable(true));
			
			statement.execute(ListaEspecificacionesDao.dropTable(true));
			statement.executeUpdate(ListaEspecificacionesDao.createTable(true));
			
			statement.execute(NovedadesProductoDao.dropTable(true));
			statement.executeUpdate(NovedadesProductoDao.createTable(true));
			
			
			statement.execute(EstadoFuenteInfoDao.dropTable(true));
			statement.executeUpdate(EstadoFuenteInfoDao.createTable(true));
			
			statement.execute(CantidadRecolectadaDao.dropTable(true));
			statement.executeUpdate(CantidadRecolectadaDao.createTable(true));
			
			statement.execute(CategoriaElementoDao.dropTable(true));
			statement.executeUpdate(CategoriaElementoDao.createTable(true));
			
			statement.execute(EspecificacionCategoriaDao.dropTable(true));
			statement.executeUpdate(EspecificacionCategoriaDao.createTable(true));
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DatabaseResourceCloser.close(statement);
			DatabaseResourceCloser.close(connection);
		}
		
	}
}
