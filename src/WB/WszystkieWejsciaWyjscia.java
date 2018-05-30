package WB;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Timer;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;


public class WszystkieWejsciaWyjscia extends JFrame {

	private JPanel contentPane;
	private JButton odswiez;
	private JTable table;
	private Timer timer;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Connection connection = RCPdatabaseConnection.dbConnector("tosia", "1234");
				try {
					WszystkieWejsciaWyjscia frame = new WszystkieWejsciaWyjscia(connection);
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 */
	public WszystkieWejsciaWyjscia(final Connection connection) {
		
		setBackground(Color.WHITE);
		setTitle("Rejestracja wej�� i wyj�� pracownik�w");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 579, 563);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblMojeMenu = new JLabel("Rejestracja wej\u015B\u0107/wyj\u015B\u0107");
		lblMojeMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMojeMenu.setFont(new Font("Century", Font.BOLD, 24));
		
		// co je�li u�ytkownik jest adminem
		
		if (Login.getAdmin()==true)
		{
		
		}
		
		JScrollPane scrollPane = new JScrollPane();
		
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(table);
//						Image img3 = new ImageIcon(this.getClass().getResource("/PdfIcon_mini.png")).getImage();
//						WylaczAlarm.setIcon(new ImageIcon(img3));
		
		ShowTable(connection, table);
						
		odswiez = new JButton("Od\u015Bwie\u017C tabel\u0119");
		odswiez.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ShowTable(connection,table);
			}
		});
		odswiez.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(odswiez, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
						.addComponent(lblMojeMenu, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(45)
					.addComponent(lblMojeMenu, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
					.addGap(26)
					.addComponent(odswiez, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(40))
		);
		contentPane.setLayout(gl_contentPane);
		
		}
	
	public void ShowTable(Connection connection, JTable table)
	{
		
		try {
			String query="SELECT access.id_karty, access.akcja, cards_name_surname_nrHacoSoft.nazwisko_imie, keys_permissions.nr_kluczy, REPLACE (access.data, 'H', ' ') AS data FROM"
					+ " access LEFT OUTER JOIN cards_name_surname_nrHacoSoft ON access.id_karty=cards_name_surname_nrHacoSoft.id_karty LEFT OUTER JOIN keys_permissions ON access.id_karty=keys_permissions.id_karty "
					+ "ORDER BY access.data desc";
			PreparedStatement pst=connection.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	}
