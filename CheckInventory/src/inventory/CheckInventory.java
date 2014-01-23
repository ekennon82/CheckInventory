package inventory;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CheckInventory  extends JFrame {
	static JLabel display = new JLabel();
	static int filmId;
	static int storeId;
	JComboBox<Film> filmList;
	JComboBox<Store> storeList;
	
	public static void printResult() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Driver loaded");
		
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/sakila?noAccessToProcedureBodies=true", "user1", "password");
		System.out.println("Database connected");
		
		CallableStatement getFilms = connection.prepareCall("{CALL sakila.film_in_stock(?, ?, ?)}");
		getFilms.setInt(1, filmId);
		getFilms.setInt(2, storeId);
		getFilms.registerOutParameter(3, Types.INTEGER);
		
		getFilms.execute();
		int returnVal = getFilms.getInt(3);
		display.setText("There are " + Integer.toString(returnVal) + " in stock at this store");
		
		connection.close();
	}
	
	public CheckInventory() throws ClassNotFoundException, SQLException {
		setLayout(new GridLayout(2,0));
		
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout());
		
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		
		filmList = new JComboBox<Film>();
		storeList = new JComboBox<Store>();
		
		JButton click = new JButton("Get Inventory");
		
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Driver loaded");
		
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/sakila?noAccessToProcedureBodies=true", "user1", "password");
		System.out.println("Database connected");
		
		Statement films = connection.createStatement();
		ResultSet filmsResult = films.executeQuery("SELECT film_id, title FROM film");
		while (filmsResult.next()) {
			Film film = new Film();
			film.setFilmId(filmsResult.getInt(1));
			film.setFilmName(filmsResult.getString(2));
			filmList.addItem(film);
		}
		
		Statement stores = connection.createStatement();
		ResultSet storesResult = stores.executeQuery("SELECT store_id FROM store");
		while (storesResult.next()) {
			Store store = new Store();
			store.setStoreId(storesResult.getInt(1));
			storeList.addItem(store);
		}

		connection.close();
		
		click.addActionListener(new clickListener());
		
		p1.add(filmList);
		p1.add(storeList);
		p1.add(click);
		
		p2.add(display);
		
		add(p1);
		add(p2);
	}
	
	public static void main(String[] args) {
		CheckInventory frame;
		try {
			frame = new CheckInventory();
			frame.setTitle("Inventory");
			frame.setSize(400,300);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public class clickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (filmList.getSelectedObjects()[0] instanceof Film)
			{
				Film film = (Film) filmList.getSelectedItem();
				filmId = film.getFilmId();
			}
			
			if (storeList.getSelectedObjects()[0] instanceof Store)
			{
				Store store = (Store) storeList.getSelectedItem();
				storeId = store.getStoreId();
			}
			
			try {
				printResult();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}